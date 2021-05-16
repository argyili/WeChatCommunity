package com.hfut.community.demo.service;


import com.hfut.community.demo.dao.PostListDao;
import com.hfut.community.demo.dao.UserDao;
import com.hfut.community.demo.domain.Post;
import com.hfut.community.demo.utils.ReturnPostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.util.Collections;

/**
 */
@Service
public class PostListService {
    /**
     */
    @Autowired
    private PostListDao postListDao;
    /**
     */
    @Autowired
    private UserDao userDao;
    /**
     * 模糊搜索
     * -1
     */
    private static final int VAGUE = -1;
    /**
     *24小时热门
     */
    private static final int HOT = 2;
    /**
     * 回复得分，因为我们项目没有对回复的点赞点踩，所以为一默认值
     * 默认值为0.5
     */
    private static final double ASCORE = 0.5;
    /**
     * log的底常量
     * 10
     */
    private static final int SINGLE1 = 10;

    /**
     * 时分秒换算常量
     * 60
     */
    private static final int SINGLE2 = 60;
    /**
     * 常量
     * 4
     */
    private static final int SINGLE3 = 4;
    /**
     * 常量
     * 5
     */
    private static final int SINGLE4 = 5;
    /**
     * 常量
     * 2
     */
    private static final int SINGLE5 = 2;
    /**
     * 常量
     * 1.5
     */
    private static final double SINGLE6 = 1.5;
    /**
     * 常量
     *
     */
    private static final double SINGLE7 = 8;
    /**
     *
     */
    private static final double SINGLE8 = 13;
    /**
     *
     */
    private static final double SINGLE9 = -12;
    /**
     *
     */
    private static final double SINGLE10 = 10000;


    /**
     * 从帖子表里查找帖子，按照所在板块及精华帖子方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndRefinement(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndRefinement(block));
    }

    /**
     * 从帖子表里查找帖子，按照所在板块及时间排序方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndTime(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndTime(block));
    }

    /**
     * 从帖子表里查找帖子，按照所在板块及点赞数排序方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndPraiseNum(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndPraiseNum(block));
    }

    /**
     * 从帖子表里查找帖子，按照所在板块及点踩数排序方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndCriticismNum(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndCriticismNum(block));
    }

    /**
     * 从帖子表里查找帖子，按照所在板块及评论数排序方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndCommentsNum(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndCommentsNum(block));
    }

    /**
     * 从帖子表里查找帖子，按照所在板块及浏览量排序方式，返回一个list到controller--马云鹏
     * @param block String
     * @return List<Post>
     */
    public List<Post> getPostListByBlockAndEyesNum(String block) {
        return ReturnPostList.getList(
                postListDao.getPostListByBlockAndEyesNum(block));
    }

    /**
     * Stack Overflow算法排序
     * 综合排序
     * 此综合排序算法中被考虑的因素有：帖子的浏览量qViews、点赞和点踩情况qScore、回复数量qAnswers、发帖时间qAge、最后一次被回复时间qUpdated。
     * @param block String 全国则为null，否则为板块名
     * @param refinement int
     * @return List<Post>
     */
    public List<Post> getStackOverflowList(String block, int refinement) {
        List<Post> result = new ArrayList<Post>();
        List<Post> temp;
        if (refinement != VAGUE) {
            if (block.equals("全国")) {
                if (refinement == HOT) {
                    temp = userDao.searchHot();
                } else {
                    temp = postListDao.getAllPostList(refinement);
                }
            } else {
                temp = postListDao.getAllPostListByBlock(block, refinement);
            }
        } else {
            temp = userDao.searchVague("%" + block + "%");
        }
        try {
            double[] scoreList = new double[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                Post post = temp.get(i);
                int qViews = post.getEyesNum();
                int qScore = post.getPraiseNum() - post.getCriticismNum();
                int qAnswers = post.getCommentsNum();
                double aScores = ASCORE * qAnswers;
                String timeTemp = post.getPostingTime();
                SimpleDateFormat qTimeTemp =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date qTime = qTimeTemp.parse(timeTemp);
                String nowTimeTemp = getBeijingTime();
                SimpleDateFormat nowTime =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date nTime = nowTime.parse(nowTimeTemp);
                long diff1 = nTime.getTime() - qTime.getTime();
                long qAge = diff1;
                String maxATemp = postListDao.getMaxCommentTime(post.getPid());
                long qUpdated;
                if (maxATemp != null) {
                    SimpleDateFormat aTimeTemp =
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date maxATime = aTimeTemp.parse(maxATemp);
                    long diff2 = nTime.getTime() - maxATime.getTime();
                    if (diff2 < diff1) {
                        qUpdated = diff2;
                    } else {
                        qUpdated = diff1;
                    }
                } else {
                    qUpdated = qAge;
                }
                double lnQViews = Math.log(qViews);
                double ln10 = Math.log(SINGLE1);
                double log10QViews = lnQViews / ln10;
                double numerator =
                        ((log10QViews * SINGLE3) + (qAnswers * qScore / SINGLE4)
                                + aScores);
                double denominator =
                        Math.pow(qAge + 1 - ((qAge - qUpdated) / SINGLE5),
                                SINGLE6);
                double score = numerator / denominator;
                scoreList[i] = score;
            }
            result = resort(scoreList, temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnPostList.getList(result);
    }

    /**
     * @param scoreList double[]
     * @param list List<Post>
     * @return List<Post>
     */
    private List<Post> resort(double[] scoreList, List<Post> list) {
        List<Post> list2 = sort(scoreList, 0, scoreList.length - 1, list);
        List<Post> result = new ArrayList<Post>();
        for (int i = list2.size() - 1; i >= 0; i--) {
            result.add(list2.get(i));
        }
        return result;
    }

    /**
     * 快速排序
     * @param array int[]
     * @param low int
     * @param high high
     * @param list List<Post>
     * @return List<Post>
     */
    private List<Post> sort(double[] array, int low, int high, List<Post> list) {
        int start = low;
        int end = high;
        double key = array[low];
        while (end > start) {
            while (end > start && array[end] >= key) {
                end--;
            }
            if (array[end] <= key) {
                double temp = array[end];
                array[end] = array[start];
                array[start] = temp;
                Collections.swap(list, start, end);
            }
            while (end > start && array[start] <= key) {
                start++;
            }
            if (array[start] >= key) {
                double temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                Collections.swap(list, start, end);
            }
        }
        if (start > low) {
            sort(array, low, start - 1, list);
        }
        if (end < high) {
            sort(array, end + 1, high, list);
        }
        return list;
    }

    /**
     * 取北京时间
     * @return String
     */
    private static String getBeijingTime() {
        return getFormatedDateString((float) SINGLE7);
    }

    /**
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset float
     * @return String
     */
    private static String getFormatedDateString(float timeZoneOffset) {
        if (timeZoneOffset > SINGLE8 || timeZoneOffset < SINGLE9) {
            timeZoneOffset = 0;
        }
        int newTime = (int) (timeZoneOffset * SINGLE2 * SINGLE2 * SINGLE10);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }
}
