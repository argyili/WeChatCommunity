package com.hfut.community.demo.service;

import com.hfut.community.demo.dao.SuperAdminDao;
import com.hfut.community.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class SuperAdminService {
    /**
     */
    @Autowired
    private SuperAdminDao superadmindao;

    /**
     * @param phoneNum String
     * @return User
     */
    public User getUserByPhone(String phoneNum) {
        return superadmindao.getUserByPhone(phoneNum);
    }

    /**
     * @param phoneNum String
     * @param region List<String>
     * @return List<Boolean>
     */
    public List<Boolean> upgradeUser(String phoneNum, List<String> region) {
        String role = superadmindao.getRole(phoneNum);
        List<Boolean> result = new ArrayList<Boolean>();
        User isadmin = null;
        isadmin = /*isadmin=*/superadmindao.isAdmin(phoneNum);
        try {
            Admin admin = new Admin(phoneNum, "1");
            if (superadmindao.upgradeUser(phoneNum) != 0
                    && superadmindao.insertAdmin(admin) != 0) {
                for (int i = 0; i < region.size(); i++) {
                    Block block = new Block(region.get(i), phoneNum);
                    try {
                        result.add(superadmindao.insertBlock(block) != 0);
                    } catch (Exception e3) {
                        result.add(false);
                    }
                }
            } else {
                result.add(false);
            }
            boolean temp = true;
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i)) {
                    temp = false;
                }
            }
            if (temp && (isadmin != null) && (region.size() > 0)) {
                superadmindao.restoreUser1(phoneNum);
                superadmindao.restoreUser2(phoneNum, role);
            }
        } catch (Exception e) {
            if ((isadmin != null)) {
                try {
                    superadmindao.restoreUser1(phoneNum);
                    superadmindao.restoreUser2(phoneNum, role);
                } catch (Exception e2) {

                }
            }
            result.add(false);
        }
        return result;
    }

    /**
     * @return List<Advertisement>
     */
    public List<Advertisement> getAllAdvertisement() {
        return superadmindao.getAllAdvertisement();
    }

    /**
     * @param aid String
     * @return boolean
     */
    public boolean deleteAdvertisement(String aid) {
        try {
            return (superadmindao.deleteAdvertisement(aid) > 0);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param aid String
     * @return Advertisement
     */
    public Advertisement getAdvertisement(String aid) {
        return superadmindao.getAdvertisement(aid);
    }

    /**
     * @param aid String
     * @param advContent String
     * @return boolean
     */
    public boolean updateAdvertisement2(String aid, String advContent) {
        return (superadmindao.updateAdvcontent(aid, advContent) > 0);
    }

    /**
     * @param advertisement Advertisement
     * @return boolean
     */
    public boolean updateAdvertisement1(Advertisement advertisement) {
        return (superadmindao.updateAdvertisement(
                advertisement.getAid(), advertisement.getAdvContent(),
                advertisement.getImg()) > 0);
    }

    /**
     * @return int
     */
    public int getAdvertisementMax() {
        return superadmindao.getAdvertisementMax();
    }

    /**
     * @param advertisement Advertisement
     * @return boolean
     */
    public boolean addAdvertisement(Advertisement advertisement) {
        return (superadmindao.addAdvertisement(advertisement) > 0);
    }

    /**
     * @param likeIt String
     * @return List<Advertisement>
     */
    public List<Advertisement> getAllAdvertisementLikeIt(String likeIt) {
        return superadmindao.getAllAdvertisementLikeIt("%" + likeIt + "%");
    }

    /**
     * @param wxid String
     * @param forbidden int
     * @return int
     **/
    public int updatebanUser(String wxid, int forbidden) {
        return superadmindao.updatebanUserImpl(wxid, forbidden);
    }
    /**
     * 返回待认证学生
     * @return List<StudentView>
     */
    public List<StudentView> searchNotIdentifiedStudent() {
        return superadmindao.searchNotIdentifiedStudent();
    }
    /**
     * 返回待认证教师
     * @return List<TeacherView>
     */
    public List<TeacherView> searchNotIdentifiedTeacher() {
        return superadmindao.searchNotIdentifiedTeacher();
    }
    /**
     * 对学生认证
     * @param wxid String
     * @param identification int
     * @return int
     */
    public int identifyStudent(String wxid, int identification) {
        return superadmindao.identifyStudent(wxid, identification);
    }
    /**
     * 对教师认证
     * @param wxid String
     * @param identification int
     * @return int
     */
    public int identifyTeacher(String wxid, int identification) {
        return superadmindao.identifyTeacher(wxid, identification);
    }
    /**
     * 搜索学生
     * @param wxid String
     * @return StudentView
     */
    public StudentView searchStudent(String wxid) {
        return superadmindao.searchStudent(wxid);
    }
    /**
     * 搜索教师
     * @param wxid String
     * @return TeacherView
     */
    public TeacherView searchTeacher(String wxid) {
        return superadmindao.searchTeacher(wxid);
    }
}
