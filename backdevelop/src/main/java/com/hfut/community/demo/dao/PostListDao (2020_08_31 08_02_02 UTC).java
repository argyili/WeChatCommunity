package com.hfut.community.demo.dao;


import com.hfut.community.demo.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Mapper
@Repository
public interface PostListDao {

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and refinement='1' and fold='0'")
    List<Post> getPostListByBlockAndRefinement(String block);

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' order by postingTime desc")
    List<Post> getPostListByBlockAndTime(String block);

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' order by praiseNum desc")
    List<Post> getPostListByBlockAndPraiseNum(String block);

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' order by criticismNum desc")
    List<Post> getPostListByBlockAndCriticismNum(String block);

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' order by commentsNum desc")
    List<Post> getPostListByBlockAndCommentsNum(String block);

    /**
     * @param block String
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' order by eyesNum desc")
    List<Post> getPostListByBlockAndEyesNum(String block);

    /**
     * @param block String
     * @param refinement int
     * @return List<Post>
     */
    @Select("select * from post "
            + "where block=#{block} and fold='0' and refinement>=#{refinement}")
    List<Post> getAllPostListByBlock(
            @Param("block")String block, @Param("refinement")int refinement);

    /**
     * @return List<Post>
     */
    @Select("select * from post where fold='0' and refinement>=#{refinement}")
    List<Post> getAllPostList(@Param("refinement")int refinement);

    /**
     * @param pid String
     * @return String
     */
    @Select("select max(commentTime) from comment where pid=#{pid}")
    String getMaxCommentTime(String pid);
}
