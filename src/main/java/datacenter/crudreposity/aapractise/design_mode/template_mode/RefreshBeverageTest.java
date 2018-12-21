package datacenter.crudreposity.aapractise.design_mode.template_mode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableAsync
@Service
public class RefreshBeverageTest {

	public static void main(String[] args) {
		
		System.out.println("制作咖啡");
		RefreshBeverage b1 = new Coffee();
		b1.prepareBeverageTemplate();
		System.out.println("咖啡制作完毕");
		
		System.out.println("\n******************************************");
		
		System.out.println("制作茶");
		RefreshBeverage b2 = new Tea();
		b2.prepareBeverageTemplate();
		System.out.println("茶制作完毕");

	}

}
