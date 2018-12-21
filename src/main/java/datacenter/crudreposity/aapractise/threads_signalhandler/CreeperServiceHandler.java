package datacenter.crudreposity.aapractise.threads_signalhandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import datacenter.crudreposity.aapractise.threads_signalhandler.CreeperService;
import java.nio.ByteBuffer;
import java.util.List;

public class CreeperServiceHandler implements CreeperService.Iface {

	final static Logger logger = LoggerFactory.getLogger(CreeperServiceHandler.class);

	@Override
	/**
	 * process creeper output, don't save the result
	 * 
	 * @author weishi
	 * @param json_data
	 *            the json string to be analyzed.
	 * @return the result json string
	 * 
	 * @param json_data
	 */
	public String process(String json_str) throws TException {
		// TODO Auto-generated method stub
		String ret = "";
		JSONObject jsonObj;
		try {
			jsonObj = JSON.parseObject(json_str);
			//jsonObj = ProcessorEntry.process(jsonObj);
			ret = jsonObj.toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TException(e);
		}
		return ret;
	}


	@Override
	/**
	 * process creeper output, and save the result
	 *
	 * @param json_data
	 *            the list of json string list to be analyzed.
	 * @return the result json string
	 *
	 * @param json_data
	 */
	public String processAndSave(List<String> json_str_list) throws TException {
		return "";
	}

	@Override
	public String processBigDataNews(String conf_json_str) throws TException {
		return "job submitted";
	}

	@Override
	public ByteBuffer getBinFile(String rowkey) throws TException {
		return null;
	}

	@Override
	public int removeBinFile(String rowkey) throws TException {
		return 0;
	}

	@Override
	public String getProspectus(String exchange, String code) throws TException {
		return "";
	}

	@Override
	public int addBinFile(String rowkey, ByteBuffer file, String extension) throws TException {
		return 0;
	}

	@Override
	public String makeProspectus(String exchange, String code, String date, String title, String source)
			throws TException {
		return "";
	}


}
