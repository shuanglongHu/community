package com.nowcoder.community;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests {
    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void test(){
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, 0, 10);
        for(DiscussPost post : discussPosts){
            System.out.println(post);
        }
        System.out.println(discussPostService.findDiscussPostRows(0));
    }

}
