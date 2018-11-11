package datacenter.crudreposity.design_mode.factorymode;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableAsync
@Service
public class SunnyTest {

	public static void main(String[] args){
		
//		HairInterface left = new LeftHair();
//		left.draw();
		
		HairFactory factory = new HairFactory();
//		HairInterface right =  factory.getHair("right");
//		right.draw();
		
//		HairInterface left = factory.getHairByClass("LeftHair");
//		left.draw();
		
		HairInterface hair = factory.getHairByClassKey("right");
		hair.draw();
		
//		PersonFactory facoty = new MCFctory();
//		Girl girl = facoty.getGirl();
//		girl.drawWomen();
		
//		PersonFactory facoty = new HNFactory();
//		Boy boy =  facoty.getBoy();
//		boy.drawMan();
	}
}
