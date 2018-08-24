package datacenter.crudreposity.config;

public class SeekConstants {
    // ********************************************************//
    // for scheduler and dispatcher module (author is dapeng)
    public static final int DATA_NORMAL = 0;
    public static final int DATA_DELETED = 1;

    public static final int JOB_NOT_REPEAT = 0;
    public static final int JOB_NEED_REPEAT = 1;

    public static final int IP_NORMAL = 0;
    public static final int IP_DEFAULT_QUALITY = 999999;
    public static final int IP_FAILED_CHECK = -1;
    public static final int IP_CANDICATE = -2;

    public static final int NOT_NEED_PROXY = 0;
    public static final int NEED_PROXY_OUTER = 1;
    public static final int NEED_PROXY_INNER = 2;

    public static final int NEW = 0;
    public static final int PROCESSING = 1;
    public static final int DONE = 2;
    public static final int ABANDON = 3;

    public static final int COOKIES_STATUS_TO_BE_REFRESHED = 0;
    public static final int COOKIES_STATUS_READY = 1;
    public static final int COOKIES_STATUS_WARNING = 2;
    public static final int COOKIES_STATUS_BAD = 3;
    public static final int COOKIES_STATUS_EXPIRED = 4;

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static final int SUCCESS = 1;

    public static final String SCHEDULE_IDS_SPLITER = ";";

    public static final int TYPE_HANDLER_QUEUE = 1;
    public static final int TYPE_SCHEDULE_QUEUE = 2;

    // if the TYPE value greater than LOGGER_TYPE_SPLITER, logginfo will be
    // message, else is number of count
    public static final int LOGGER_TYPE_SPLITER = 0;

    public static final int LOGGER_TYPE_SUCC = -1;
    public static final int LOGGER_TYPE_FETCHER_REJECT = -2;
    public static final int LOGGER_TYPE_REFILE_TO_PQ = -3;
    public static final int LOGGER_TYPE_GET_FROM_PQ = -4;
    public static final int LOGGER_TYPE_REFILE_TO_SCHEDULE = -5;

    public static final int LOGGER_TYPE_DROP = -6;
    public static final int LOGGER_TYPE_GET_FROM_HANDLER_MQ = -7;
    public static final int LOGGER_TYPE_GET_FROM_DB = -8;
    public static final int LOGGER_TYPE_GET_FROM_SCHEDULER_MQ = -9;
    public static final int LOGGER_TYPE_EXPIRES_DROP = -10;
    public static final int LOGGER_TYPE_DEBUG = 1;
    public static final int LOGGER_TYPE_WARN = 2;
    public static final int LOGGER_TYPE_INFO = 3;
    public static final int LOGGER_TYPE_ERROR = 4;

    public static String CONF_DIR = "src/main/resources/";

    // ********************************************************//
    // for fetcher module (author is zhanghu)
    // ********************************************************//
    // for handler module (author is tianbi)
    public static final int REDIS_DEFAULT_PORT = 6379;
    public static final int HALF_DAY_MS = 12 * 60 * 60 * 1000;
    public static final String PARSE_QUEUE_NAME = "Taobao-Crawler-Queue";
    public static final boolean DEBUG = true;
    public static final String DOC_KEY_DUP = "doc_key_dup";
    public static int TAOBAO_FETCH_TYPE_ID = 5;
    public static int HTML_FETCH_TYPE_ID = 2;
    public static int BROWER_FETCH_TYPE_ID = 3;
    public static int SELENIUM_FETCH_TYPE_ID = 4;
    public static int NORMAL_SELENIUM_FETCH_TYPE_ID = 5;
    public static int TMALL_WEAR_FETCH_TYPE_ID = 9;
    public static final String TAOBAOSHU_MARKET = "App.set(\"all_data\",";
    public static final String TAOBAOSHU_RANK = "";
    public static final String TAOBAOSHU_MARKET_FROM = "TaobaoShu-Market";
    public static final String TAOBAOSHU_RANK_FROM = "";
    public static boolean USE_FILTER_STRATEGOY = true;
    public static final int PNAME_MAX_LEN = 300;
    public static final int MAX_URL_SIZE = 1000;
    public static final String SPLIT = ";";
    public static final int SHU_TAOBAO_EXTRACT_TYPE_ID = 10000;
    public static final int YHD_COSMETIC_TYPE_ID = 10001;
    public static final int NAVIGATION_ID_Compatitor = 1;
    public static final int NAVIGATION_ID_BRAND = 3;
    public static final int NAVIGATION_ID_STAR = 5;
    // ********************************************************//
    // for deduplication module (xingrong)
    public static final int DUMP_REDIS_DB = 13;
    public static final int URL_REDIS_DB = 11;
    // ********************************************************//
}
