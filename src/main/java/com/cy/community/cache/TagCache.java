package com.cy.community.cache;

import com.cy.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cy
 * @since 2019-11-07 14:13
 */
public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagsDTO = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++",
                "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less",
                "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagsDTO.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails",
                "tornado", "koa", "struts"));
        tagsDTO.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡",
                "unix", "hadoop", "windows-server"));
        tagsDTO.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver",
                "postgresql", "sqlite"));
        tagsDTO.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea",
                "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagsDTO.add(tool);
        return tagsDTO;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagsDTO = get();

        List<String> tagList = tagsDTO
                .stream()
                .map(TagDTO::getTags)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t))
                .collect(Collectors.joining(","));
    }

}