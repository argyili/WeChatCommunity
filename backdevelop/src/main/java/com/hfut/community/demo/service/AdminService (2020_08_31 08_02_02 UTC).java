
package com.hfut.community.demo.service;

import com.hfut.community.demo.dao.AdminDao;
import com.hfut.community.demo.domain.Admin;
import com.hfut.community.demo.domain.Block;
import com.hfut.community.demo.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
/**
 */
public class AdminService {
    /**
     */
    @Autowired
    private AdminDao adminDao;
    /**
     * @param pid int
     */
    public void upgradeRefinement(int pid) {
        adminDao.upgradeRefinement(pid);
    }
    /**
     * @param pid int
     * @return Post
     */
    public Post post(int pid) {
        Post p = null;
        p = adminDao.isPost(pid);
        return p;
    }
    /**
     * @param pid int
     */
    public void forbidComment(int pid) {
        adminDao.forbidCommentPost(pid);
    }
    /**
     * @param title String
     * @return List
     */
    public List<Post> searchPost(String title) {
        return adminDao.searchPost("%" + title + "%");
    }
    /**
     * @return List
     */
    public List<Post> searchAllPost() {
        return adminDao.searchAllPost();
    }
    /**
     * @return List
     */
    public List<Post> searchByTime() {
        return adminDao.searchByTime();
    }
    /**
     * @return List
     */
    public List<Post> searchByPraisenum() {
        return adminDao.searchByPraisenum();
    }
    /**
     * @return List
     */
    public List<Post> searchByCriticismnum() {
        return adminDao.searchByCriticismnum();
    }
    /**
     * @return List
     */
    public List<Post> searchByCommentsnum() {
        return adminDao.searchByCommentsnum();
    }
    /**
     * @return List
     */
    public List<Post> searchByEyesnum() {
        return adminDao.searchByEyesnum();
    }
    /**
     * @return Admin
     * @param phoneNum String
     */
    public Admin login(String phoneNum) {
        return adminDao.login(phoneNum);
    }
    /**
     * @param pid int
     */
    public void deletePost(int pid) {
        try {
            adminDao.deleteChoice(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            adminDao.deleteComment(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            adminDao.deletePost(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据手机号查找是否为板块管理员
     * @param phonenum String
     * @return int
     */
    public Admin judgeIsExist(String phonenum) {
        return adminDao.judgeIsExistImpl(phonenum);
    }

    /**
     * 折叠帖子
     * @param pid int
     * @return int
     */
    public int foldPost(int pid) {
        return adminDao.foldPost(pid);
    }

    /**
     * 根据电话号码得到管理员管理的所有版块
     * @param phonenum String
     * @return List
     */
    public List<Block> searchBlock(String phonenum) {
        return adminDao.searchBlock(phonenum);
    }
    /**
     * @return List
     * @param block String
     */
    public List<Post> searchByBlock(String block) {
        return adminDao.searchByBlock(block);
    }
    /**
     * @return List
     * @param block String
     */
    public List<Post> searchByBlock2(String block) {
        return adminDao.searchByBlock2(block);
    }
    /**
     * @return List
     * @param block String
     */
    public List<Post> searchByBlock3(String block) {
        return adminDao.searchByBlock3(block);
    }
    /**
     * @return List
     * @param block String
     */
    public List<Post> searchByBlock4(String block) {
        return adminDao.searchByBlock4(block);
    }
}
