package com.hfut.community.demo.controller;

import com.hfut.community.demo.domain.Admin;
import com.hfut.community.demo.domain.Block;
import com.hfut.community.demo.domain.Post;
import com.hfut.community.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Controller
public class AdminController {
    /**
     */
    @Autowired
    private AdminService adminService;
    /**
     * @param foldPost Post
     * @param model Model
     * @param request  Model
     * @param map ModelMap
     * @param response HttpServletResponse
     * @param session HttpSession
     * @return String
     */
    @RequestMapping(value = "/{prefix}/refinementPostBlock")
    public String refinementPostBlock(
            @ModelAttribute(value = "post") Post foldPost, Model model,
            ModelMap map, HttpServletResponse response,
            HttpServletRequest request, HttpSession session,
            @PathVariable(value = "prefix") String prefix) {
        String preString = prefix.equals("Admin") ? "admin" : "admin/superAdmin";
        String selectedPid = foldPost.getCheckedId();
        if (foldPost != null && !"".equals(selectedPid) && selectedPid != null) {
            List<Post> refinementPost = new ArrayList<Post>();
            int len = 0;
            try {
                String[] arrayPid = selectedPid.split(";");
                for (len = 0; len < arrayPid.length; len++) {
                    int pid = Integer.valueOf(arrayPid[len]);
                    adminService.upgradeRefinement(pid);
                    Post p = adminService.post(pid);
                    refinementPost.add(p);
                    model.addAttribute("searchBatchPostResult", "batchRefinement");
                    model.addAttribute("BatchPost", refinementPost);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (len > 0) {
                return preString + "/batchSuccess";
            }
        }
        Admin admin = (Admin) session.getAttribute("phone");
        String phone = admin.getPhoneNum();
        List<Post> allPost = new ArrayList<Post>();
        if ("0".equals(admin.getPower())) {
            allPost = adminService.searchAllPost();
        } else {
            List<Block> block = adminService.searchBlock(phone);
            model.addAttribute("block", block);
            for (int i = 0; i < block.size(); i++) {
                List<Post> post =
                        adminService.searchByBlock(block.get(i).getBlock());
                for (int j = 0; j < post.size(); j++) {
                    allPost.add(post.get(j));
                }
            }
        }
        model.addAttribute("searchPost", allPost);
        return preString + "/refinementPostByBlock";
    }

    /**
     * @param foldPost Post
     * @param model Model
     * @param request  Model
     * @param map ModelMap
     * @param response HttpServletResponse
     * @param session HttpSession
     * @return String
     */
    @RequestMapping(value = "/{prefix}/forbidPostBlock")
    public String forbidPostBlock(
            @ModelAttribute(value = "post") Post foldPost, Model model,
            ModelMap map, HttpServletResponse response,
            HttpServletRequest request, HttpSession session,
            @PathVariable(value = "prefix") String prefix) {
        String preString = prefix.equals("Admin") ? "admin" : "admin/superAdmin";
        String forbidPid = foldPost.getCheckedId();
        if (foldPost != null && (!"".equals(forbidPid)) && (forbidPid != null)) {
            List<Post> refinementPost = new ArrayList<Post>();
            int len = 0;
            try {
                String[] arrayPid = forbidPid.split(";");
                for (len = 0; len < arrayPid.length; len++) {
                    int pid = Integer.valueOf(arrayPid[len]);
                    adminService.forbidComment(pid);
                    Post p = adminService.post(pid);
                    refinementPost.add(p);
                    model.addAttribute("searchBatchPostResult", "batchRefinement");
                    model.addAttribute("BatchPost", refinementPost);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (len > 0) {
                return preString + "/forbidSuccess";
            }
        }
        Admin admin = (Admin) session.getAttribute("phone");
        String phone = admin.getPhoneNum();
        List<Post> allPost = new ArrayList<Post>();
        if ("0".equals(admin.getPower())) {
            allPost = adminService.searchAllPost();
        } else {
            List<Block> block = adminService.searchBlock(phone);
            model.addAttribute("block", block);
            for (int i = 0; i < block.size(); i++) {
                List<Post> post =
                        adminService.searchByBlock2(block.get(i).getBlock());
                for (int j = 0; j < post.size(); j++) {
                    allPost.add(post.get(j));
                }
            }
        }
        model.addAttribute("searchPost", allPost);
        return preString + "/forbidCommentPostByBlock";
    }


    /**
     * @param foldPost Post
     * @param model Model
     * @param request  Model
     * @param map ModelMap
     * @param response HttpServletResponse
     * @param session HttpSession
     * @return String
     */
    @RequestMapping(value = "/{prefix}/deletePostBlock")
    public String deletePostBlock(
            @ModelAttribute(value = "post") Post foldPost, Model model,
            ModelMap map, HttpServletResponse response,
            HttpServletRequest request, HttpSession session,
            @PathVariable(value = "prefix") String prefix) {
        String preString = prefix.equals("Admin") ? "admin" : "admin/superAdmin";
        String forbidPid = foldPost.getCheckedId();
        if (foldPost != null && (!"".equals(forbidPid)) && (forbidPid != null)) {
            List<Post> deletePost = new ArrayList<Post>();
            int len = 0;
            try {
                String[] arrayPid = forbidPid.split(";");
                for (len = 0; len < arrayPid.length; len++) {
                    int pid = Integer.valueOf(arrayPid[len]);
                    Post p = adminService.post(pid);
                    adminService.deletePost(pid);
                    deletePost.add(p);
                    model.addAttribute("searchBatchPostResult", "batchRefinement");
                    model.addAttribute("BatchPost", deletePost);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (len > 0) {
                return preString + "/deleteSuccess";
            }
        }
        Admin admin = (Admin) session.getAttribute("phone");
        String phone = admin.getPhoneNum();
        List<Post> allPost = new ArrayList<Post>();
        if ("0".equals(admin.getPower())) {
            allPost = adminService.searchAllPost();
        } else {
            List<Block> block = adminService.searchBlock(phone);
            model.addAttribute("block", block);
            for (int i = 0; i < block.size(); i++) {
                List<Post> post =
                        adminService.searchByBlock3(block.get(i).getBlock());
                for (int j = 0; j < post.size(); j++) {
                    allPost.add(post.get(j));
                }
            }
        }
        model.addAttribute("searchPost", allPost);
        return preString + "/deletePostByBlock";
    }


    /**
     * @param foldPost Post
     * @param model Model
     * @param request  Model
     * @param map ModelMap
     * @param response HttpServletResponse
     * @param session HttpSession
     * @return String
     */
    @RequestMapping(value = "/{prefix}/foldPostBlock")
    public String foldPostBlock(
            @ModelAttribute(value = "post") Post foldPost, Model model,
            ModelMap map, HttpServletResponse response,
            HttpServletRequest request, HttpSession session,
            @PathVariable(value = "prefix") String prefix) {
        String preString = prefix.equals("Admin") ? "admin" : "admin/superAdmin";
        String forbidPid = foldPost.getCheckedId();
        if (foldPost != null && (!"".equals(forbidPid)) && (forbidPid != null)) {
            List<Post> deletePost = new ArrayList<Post>();
            int len = 0;
            try {
                String[] arrayPid = forbidPid.split(";");
                for (len = 0; len < arrayPid.length; len++) {
                    int pid = Integer.valueOf(arrayPid[len]);
                    adminService.foldPost(pid);
                    Post p = adminService.post(pid);
                    deletePost.add(p);
                    model.addAttribute("searchBatchPostResult", "batchRefinement");
                    model.addAttribute("BatchPost", deletePost);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (len > 0) {
                return preString + "/foldSuccess";
            }
        }
        Admin admin = (Admin) session.getAttribute("phone");
        String phone = admin.getPhoneNum();
        List<Post> allPost = new ArrayList<Post>();
        if ("0".equals(admin.getPower())) {
            allPost = adminService.searchAllPost();
        } else {
            List<Block> block = adminService.searchBlock(phone);
            model.addAttribute("block", block);
            for (int i = 0; i < block.size(); i++) {
                List<Post> post =
                        adminService.searchByBlock4(block.get(i).getBlock());
                for (int j = 0; j < post.size(); j++) {
                    allPost.add(post.get(j));
                }
            }
        }
        model.addAttribute("searchPost", allPost);
        return preString + "/foldPostByBlock";
    }
}

