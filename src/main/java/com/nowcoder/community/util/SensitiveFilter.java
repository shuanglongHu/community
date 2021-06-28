package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

    //服务启动的时候调用,根据敏感词创建前缀树
    @PostConstruct
    public void init(){
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while((keyword = reader.readLine()) != null) {
                //添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (Exception e) {
           logger.error("加载敏感词文件失败：" + e.getMessage());
        }
        
    }

    //将一个敏感词添加到前缀树中去
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for(int i = 0; i < keyword.length(); i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode == null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指针指向子节点，进入下一轮循环
            tempNode = subNode;

            //设置结束标识
            if(i == keyword.length() - 1){
                tempNode.setKeywordEnd(true);
            }
        }
    }
    /*
        过滤敏感词，text：待过滤的文本
        @return 过滤后的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        //指针一，默认指向根
        TrieNode tempNode = rootNode;
        //指针二，指针三
        int begin = 0;
        int position = 0;
        //结果，使用StringBuilder存储
        StringBuilder stringBuilder = new StringBuilder();
        while(position < text.length()){
            char c = text.charAt(position);

            //跳过符号
            if(isSymbol(c)){
                //若指针一处于根节点,将此符号计入结果，让指针二走向下一步
                if(tempNode == rootNode){
                    stringBuilder.append(c);
                    begin++;
                }
                //无论符号在开头或中间，指针三都向下走一步
                position++;
                continue;
            }
            //不是特殊符号，检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                //以begin开头的不为敏感词
                stringBuilder.append(text.charAt(begin));
                position = ++begin;
                //重新指向根节点
                tempNode = rootNode;
            } else if(tempNode.isKeywordEnd()) {
                //发现了敏感词，将begin到position的字符串替换掉
                stringBuilder.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                //继续检查下一个字符
                position++;
            }
        }
        //将最后一批字符计入结果
        stringBuilder.append(text.substring(begin));
        return stringBuilder.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        //c < 0x2E80 || c > 0x9FFF 为东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }
    //前缀树
    private class TrieNode{
        //描述关键词结束的标识
        private boolean isKeywordEnd = false;

        //描述当前节点的子节点(key是下级字符，value是下级节点）
        private Map<Character,TrieNode> subNodes = new HashMap<>();


        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }


        //添加子节点的方法
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c,node);
        }

        //获取子节点的方法
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
