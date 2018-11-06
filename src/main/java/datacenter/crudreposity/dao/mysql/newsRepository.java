package datacenter.crudreposity.dao.mysql;

import datacenter.crudreposity.config.ConnectionJustify;
import datacenter.crudreposity.config.DbContextHolder;
import datacenter.crudreposity.entity.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Repository
public interface newsRepository extends CrudRepository<News, Long> {

    @Query(name = "getNews", value = "SELECT id,other_info,title,news_time,junior_channel FROM news WHERE junior_channel LIKE '%美股%' AND junior_channel != '美股' AND  junior_channel != '美股公告'  AND  junior_channel != '美股热点' AND news_time >'2018-10-25 09:29:59'\n" +
            "AND column_code = '' ORDER BY news_time DESC", nativeQuery = true)
    public ArrayList<News> getNews();




}



