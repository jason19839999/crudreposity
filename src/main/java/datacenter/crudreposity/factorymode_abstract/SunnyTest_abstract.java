package datacenter.crudreposity.factorymode_abstract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


@SpringBootApplication
@EnableAsync
@Service
public class SunnyTest_abstract {

	public static void main(String[] args) throws Exception{
		
//		HairInterface left = new LeftHair();
//		left.draw();
		
//		HairFactory factory = new HairFactory();
//		HairInterface right =  factory.getHair("right");
//		right.draw();
		
//		HairInterface left = factory.getHairByClass("com.sunny.project.LeftHair");
//		left.draw();
		
//		HairInterface hair = factory.getHairByClassKey("in");
//		hair.draw();
		
//		PersonFactory facoty = new MCFctory();
//		Girl girl = facoty.getGirl();
//		girl.drawWomen();
		
		PersonFactory facoty = new HNFactory();
		Boy boy =  facoty.getBoy();
		boy.drawMan();
		Girl girl = facoty.getGirl();
		girl.drawWomen();

		PersonFactory facotyMC = new MCFactory();
		Boy boyMC = facotyMC.getBoy();
		boyMC.drawMan();
		Girl girlMC = facotyMC.getGirl();
		girlMC.drawWomen();


//		String args2[] = new String[2];
//		args2[0] = "-c";
//		args2[1] = SeekConstants.CONF_DIR + "/creeper_service.properties";
//		State state = new State(args2);
//		MybatisSessionFactory.init(state);
		SpringApplication springApplication = new SpringApplication(SunnyTest_abstract.class);
		springApplication.run(args);

	}
}
