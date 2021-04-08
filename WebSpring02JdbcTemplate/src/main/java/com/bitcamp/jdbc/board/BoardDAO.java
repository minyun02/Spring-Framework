package com.bitcamp.jdbc.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.bitcamp.jdbc.Constants;

public class BoardDAO implements BoardDAOImpl {
	public JdbcTemplate template;
	public JdbcTemplate getTemplate() {
		return template;
	}
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public BoardDAO() {
		template = Constants.jdbcTemplate;
	}
	
	
	@Override
	public List<BoardVO> boardAllRecord() {
		String sql = "select no, subject, userid, hit, "
				+ " to_char(writedate, 'MM-DD HH:MI') writedate from board order by no desc";
		//        select를 실행해서 vo에 데이터를 세팅하고 List에 vo객체를 추가하여 리턴해준다.
		return template.query(sql, new BeanPropertyRowMapper<BoardVO>(BoardVO.class));		
	}

	@Override
	public BoardVO boardOneRecord(int no) {
		String sql = "select no, subject, content, userid, hit, writedate from board where no=?";
//		Object[] a = new Object[1];
//		a[0] = no;
		return template.queryForObject(sql, new Object[] {no}, new BeanPropertyRowMapper<BoardVO>(BoardVO.class));
	}

	@Override
	public int boardInsertRecord(final BoardVO vo) {
		try {
			String sql = "insert into board(no, subject, userid, content, ip) "
					+ " values(boardsq.nextval, ?, ?, ?, ?)";
			// template.update(sql, vo.getSubject(), vo.getUserid(), vo.getContent(), vo.getIp()); <- int가 돌아온다.
			return template.update(sql, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, vo.getSubject());
					ps.setString(2, vo.getUserid());
					ps.setString(3, vo.getContent());
					ps.setString(4, vo.getIp());
				}
			});
		}catch(Exception e) {
			System.out.println("글쓰기 에러발생");
			e.printStackTrace();
			return 0;
		}
	}	
	@Override
	public int boardUpdateRecord(BoardVO vo) {
		String sql = "update board set subject=?, content=? where no=? and userid=?";
		return template.update(sql, vo.getSubject(), vo.getContent(), vo.getNo(), vo.getUserid());
	}

	@Override
	public int boardDeleteRecord(final int no, final String userid) {
		String sql = "delete from board where no=? and userid=?";
		return template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, no);
				ps.setString(2, userid);
			}
		});
	}

	@Override
	public void hitCount(int no) {
		String sql = "update board set hit=hit+1 where no=?";
		template.update(sql, no);
	}

}
