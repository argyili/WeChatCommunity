package com.hfut.community.demo.dao;

import com.hfut.community.demo.domain.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Mapper
@Repository
public interface SuperAdminDao {

    /**
     * @param phoneNum String
     * @return String
     */
    @Select("select role from user where phoneNum=#{phoneNum}")
    String getRole(String phoneNum);

    /**
     * @param phoneNum String
     * @return User
     */
    @Select("select * from user where phoneNum=#{phoneNum}")
    User getUserByPhone(String phoneNum);

    /**
     * @param phoneNum String
     * @return User
     */
    @Select("select * from user where phoneNum=#{phoneNum} and role!='4'")
    User isAdmin(String phoneNum);

    /**
     * @param phoneNum String
     * @return int
     */
    @Update("update user set role='4' where phoneNum=#{phoneNum} and role!='4'")
    int upgradeUser(String phoneNum);

    /**
     * @param newUser Admin
     * @return int
     */
    @Insert("insert into admin(phoneNum,power) value(#{phoneNum},#{power})")
    int insertAdmin(Admin newUser);

    /**
     * @param block Block
     * @return int
     */
    @Insert("insert into block(block,phoneNum) value(#{block},#{phoneNum})")
    int insertBlock(Block block);

    /**
     * @param phoneNum String
     * @return int
     */
    @Delete("delete from admin where phoneNum=#{phoneNum} and power='1'")
    int restoreUser1(String phoneNum);

    /**
     * @param phoneNum String
     * @param role String
     * @return int
     */
    @Update("update user set role=#{role} where phoneNum=#{phoneNum} and role='4'")
    int restoreUser2(String phoneNum, String role);

    /**
     * @return List<Advertisement>
     */
    @Select("select * from advertisement")
    List<Advertisement> getAllAdvertisement();

    /**
     * @param aid String
     * @return int
     */
    @Delete("delete from advertisement where aid=#{aid}")
    int deleteAdvertisement(String aid);

    /**
     * @param aid String
     * @return Advertisement
     */
    @Select("select * from advertisement where aid=#{aid}")
    Advertisement getAdvertisement(String aid);

    /**
     * @param aid String
     * @param advContent String
     * @return int
     */
    @Update("update advertisement set advContent=#{advContent} where aid=#{aid}")
    int updateAdvcontent(@Param("aid") String aid, @Param("advContent") String advContent);

    /**
     * @param aid String
     * @param advContent String
     * @param img String
     * @return
     */
    @Update("update advertisement set advContent=#{advContent},img=#{img} where aid=#{aid}")
    int updateAdvertisement(@Param("aid") String aid, @Param("advContent") String advContent, @Param("img") String img);

    /**
     * @return int
     */
    @Select("select max(aid) from advertisement")
    int getAdvertisementMax();

    /**
     * @param advertisement Advertisement
     * @return int
     */
    @Insert("insert into advertisement(aid,img,advContent) values (#{aid},#{img},#{advContent})")
    int addAdvertisement(Advertisement advertisement);

    /**
     * @param likeIt String
     * @return List<Advertisement>
     */
    @Select("select * from advertisement where advContent like #{likeIt}")
    List<Advertisement> getAllAdvertisementLikeIt(String likeIt);

    /**
     * @param wxid String
     * @param forbidden int
     * @return int
     **/
    @Update("update user SET forbidden=#{param2} WHERE wxid=#{param1}")
    int updatebanUserImpl(String wxid, int forbidden);
    /**
     * 返回待认证的学生
     * @return List<StudentView>
     */
    @Select("select * from studentView where identification=2")
    List<StudentView> searchNotIdentifiedStudent();
    /**
     * 返回待认证的教师
     * @return List<TeacherView>
     */
    @Select("select * from teacherView where identification=2")
    List<TeacherView> searchNotIdentifiedTeacher();
    /**
     * 处理学生认证
     * @param wxid String
     * @param identification int
     * @return int
     */
    @Update("update student set identification=#{identification} where wxid=#{wxid}")
    int identifyStudent(@Param("wxid") String wxid, @Param("identification") int identification);
    /**
     * 处理教师认证
     * @param wxid String
     * @param identification int
     * @return int
     */
    @Update("update teacher set identification=#{identification} where wxid=#{wxid}")
    int identifyTeacher(@Param("wxid") String wxid, @Param("identification") int identification);
    /**
     * 搜索学生
     * @param wxid String
     * @return StudentView
     */
    @Select("select * from studentView where wxid=#{wxid}")
    StudentView searchStudent(@Param("wxid") String wxid);
    /**
     * 搜索教师
     * @param wxid String
     * @return TeacherView
     */
    @Select("select * from teacherView where wxid=#{wxid}")
    TeacherView searchTeacher(@Param("wxid") String wxid);
}
