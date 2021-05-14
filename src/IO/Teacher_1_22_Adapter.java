package IO;
//wps上文档，和装饰器类似，但用的很少，只是作为知识体系的一部分


/*
手机电源适配：各种手机厂商生成的手机电源接口不尽相同，就存在不同的电源接口的适配，
将一个USB充电口适配成TypeC充电口
* */

/**
 * 被适配类：USB
 * 存在一个USB充电口
 * 该充电口已经实现其特定的USB接口的能力
 */
 class USB {
	void isUSB(){
		System.out.println("USB充电口");
	}
}

/**
 * 目标类：TypeC
 * TypeC接口
 * 即需要的目标接口
 */
 interface TypeC {
	public void isTypeC();
}


/**
 * 类适配器
 * 核心类:将USB适配成TypeC
 */
 class ClassAdapter extends USB implements TypeC {

	@Override
	public void isTypeC() {
		//TypeC接口的实现是通过适配USB来实现的
		super.isUSB();
	}
}


/**
 * 对象适配器
 * 将被适配的对象作为适配器的属性，通过构造函数获取实例
 */
 class ObjectAdapter implements TypeC{
	private USB usb;
	public ObjectAdapter(USB usb) {
		this.usb = usb;
	}

	@Override
	public void isTypeC() {
		usb.isUSB();
	}
}


//客户类：
public class Teacher_1_22_Adapter {
	public static void main(String[] args) {
		//类适配器的使用
		TypeC typeC = new ClassAdapter();
		typeC.isTypeC();

        //对象适配器的使用
		TypeC typeC2 = new ObjectAdapter(new USB());
		typeC2.isTypeC();
	}

}
