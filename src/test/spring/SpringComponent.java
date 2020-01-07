package test.spring;

import org.springframework.stereotype.Component;

@Component//不写value默认bean为类名首字母小写名称，即为springComponent,相当于配置文件<bean id="springComponent" class="当前类全路径">
public class SpringComponent {
	public SpringComponent() {
		System.out.println("Bean实例化成功!");
	}
	
	public void execute() {
		System.out.println("Bean方法已执行!");
	}

}
