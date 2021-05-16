package com.hfut.community.demo.utils;

import com.hfut.community.demo.domain.Post;
import java.util.ArrayList;
import java.util.List;

/**
 */
public final class ReturnPostList {
    /**
     */
    private ReturnPostList() {
    }
    /**
     */
    private static final int MAX_LENGTH = 100;

    /**
     * @param list List<Post>
     * @return List<Post>
     */
    public static List<Post> getList(List<Post> list) {
        List<Post> result = new ArrayList<>();
        for (int i = 0; i < MAX_LENGTH  && i < list.size(); i++) {
            result.add(list.get(i));
        }
        return result;
    }
}
