package datacenter.crudreposity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang.gao on 2017/4/13.
 */

@Component
public class MysqlCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MysqlCommandLineRunner.class);

//    @Autowired
//    private AppChannelRepository appChannelRepository;
//
//    @Autowired
//    private AppColumnsRepository appColumnsRepository;
//
//    public static Map<String,String> channelNameCodeMap = new HashMap<String,String>();
//    public static Map<String,String> columNameCodeMapp = new HashMap<String,String>();
//    public static Map<String,String> channelCodeNameMap = new HashMap<String,String>();
//    public static List<AppChannel> allChannelShowName = new ArrayList<AppChannel>();
//    public static List<AppChannel> fixedChannels= new ArrayList<>();
//    public static Map<String,String> channelCodeShowNameMap = new HashMap<String,String>();
//
    @Override
    public void run(String... args) throws Exception {

//        ArrayList<AppChannel> allAppChannels = appChannelRepository.getAllAppChannels();
//        for (AppChannel channel:allAppChannels) {
//            channelNameCodeMap.put(channel.getChannel_name(),channel.getChannel());
//            channelCodeNameMap.put(channel.getChannel(),channel.getChannel_name());
//            channelCodeShowNameMap.put(channel.getChannel(),channel.getShow_name());
//        }
//        //初始化栏目名称和code数据，供栏目数据处理用
//        ArrayList<AppColumns> allnewsColumns = appColumnsRepository.getAllColumms();
//        for (AppColumns column:allnewsColumns) {
//            columNameCodeMapp.put(column.getColumn_code(),column.getColumn_name());
//        }
//
//
//        allChannelShowName = appChannelRepository.getAllAppChannelShowNameByType(1);
//        for(AppChannel c : allAppChannels){
//            if(c.getIsfix() == 1){
//                fixedChannels.add(c);
//            }
//        }
        System.out.println("CommandLineRunner → MysqlCommandLineRunner");
    }

}
