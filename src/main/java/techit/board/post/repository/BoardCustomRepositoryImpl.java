package techit.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import techit.board.post.entity.Board;
import techit.board.post.service.form.ModifyForm;
import techit.board.post.service.form.SearchForm;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Transactional(readOnly = true)
@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository{
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public BoardCustomRepositoryImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("board")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void updateBoard(ModifyForm form) {
        String sql = "update board set title=:title, content=:content, updated_at=:updated_at, password=:password " +
                "where id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", form.getTitle())
                .addValue("content", form.getContent())
                .addValue("password", form.getPassword())
                .addValue("updated_at", LocalDateTime.now())
                .addValue("id", form.getId());

        template.update(sql, param);
    }

    @Override
    public Page<Board> findAllByTitle(SearchForm form, Pageable pageable) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        String key = form.getKey();
        String value = form.getValue();
        // Count query
        String countSql = "select count(*) from board";
        String sql = "select * from board";

        if (key != null) {
            if (key.equals("title")) {
                if (StringUtils.hasText(value)) {
                    countSql += " where title like concat('%',:value,'%') or content like concat('%',:value,'%')";
                    sql += " where title like concat('%',:value,'%') or content like concat('%',:value,'%')";
                    param.addValue("value", value);
                }
            }

            if (key.equals("name")) {
                if (StringUtils.hasText(value)) {
                    countSql += " where name like concat('%',:value,'%')";
                    sql += " where name like concat('%',:value,'%')";
                    param.addValue("value", value);
                }
            }
        }

        // 총 레코드 수 계산
        int total = template.queryForObject(countSql, param, Integer.class);

        // 페이징 파라미터 추출
        int size = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        // 정렬 조건 처리
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            StringBuilder sortSql = new StringBuilder(" order by ");
            for (Sort.Order order : sort) {
                sortSql.append(order.getProperty())
                        .append(" ")
                        .append(order.getDirection().name())
                        .append(", ");
            }
            // 마지막 콤마와 공백 제거
            sortSql.delete(sortSql.length() - 2, sortSql.length());
            sql += sortSql.toString();
        }

        // 제한 및 오프셋 추가
        param.addValue("size", size);
        param.addValue("offset", offset);
        sql += " limit :size offset :offset";

        // 결과 가져오기
        List<Board> boards = template.query(sql, param, postRowMapper());

        return new PageImpl<>(boards, pageable, total);
    }

    //    @Transactional
//    @Override
//    public Long save(Board board) {
//        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(board);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        board.setId(key.longValue());
//        return board.getId();
//
//    }
//
//    @Override
//    public Optional<Board> findOne(Long id) {
//        String sql = "select * from board where id = :id";
//        try{
//            Map<String, Object> param = Map.of("id", id);
//            Board board = template.queryForObject(sql, param, postRowMapper());
//            return Optional.of(board);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//    @Transactional
//    @Override
//    public void delete(Long id) {
//        String sql = "delete from board where id = :id";
//        template.update(sql, Map.of("id", id));
//    }
//    @Transactional
//    @Override
//    public void update(ModifyForm form) {
//        String sql = "update board set title=:title, content=:content, updated_at=:updated_at, password=:password " +
//                "where id=:id";
//        MapSqlParameterSource param = new MapSqlParameterSource()
//                .addValue("title", form.getTitle())
//                .addValue("content", form.getContent())
//                .addValue("password", form.getPassword())
//                .addValue("updated_at", LocalDateTime.now())
//                .addValue("id", form.getId());
//
//        template.update(sql, param);
//    }

    private RowMapper<Board> postRowMapper(){
        return BeanPropertyRowMapper.newInstance(Board.class);
    }

}
