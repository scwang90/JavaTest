package com.test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ClassTypeTest {
	
	public Map<String , Integer> map = new HashMap<String, Integer>();   

	public static class Base<T,TT>{
		protected Class<T> mclazz;
		protected Class<TT> mclazzt;
		
		@SuppressWarnings("unchecked")
		public Base() {
			// TODO Auto-generated constructor stub
			mclazz = (Class<T>)getActualTypeArgument(this,Base.class,0);
			System.out.println("clazz = "+mclazz);
			System.out.println();
			mclazzt = (Class<TT>)getActualTypeArgument(this,Base.class,1);
			System.out.println("clazz = "+mclazzt);
//			Class<? extends Base> clazz = this.getClass();
//			System.out.println(clazz);
//			Type type = clazz;
//			if (type instanceof ParameterizedType) {
//				ParameterizedType ptype = ParameterizedType.class.cast(type);
//				Type[] ptypeas = ptype.getActualTypeArguments();
//				for (Type ttype : ptypeas) {
//					System.out.println("Arguments-"+ttype);
//				}
//			}
//			Type supet = clazz.getGenericSuperclass();
//			System.out.println(supet);
//			if (supet instanceof ParameterizedType) {
//				ParameterizedType ptype = ParameterizedType.class.cast(supet);
//				Type[] ptypeas = ptype.getActualTypeArguments();
//				for (Type ttype : ptypeas) {
//					System.out.println("Arguments-"+ttype);
//				}
//			}
		}
		/**
		 * 获取 subobj 相对 父类 supclass 的 第index个泛型参数
		 * @param subobj 对象 一般用this
		 * @param supclass 父类 一般用自己的类class
		 * @param index 要获取参数的序列 一般用0
		 * @return null 查找失败 否则返回参数类型Class<?>
		 */
		public Class<?> getActualTypeArgument(Object subobj,Class<?> supclass,int index){
			Class<?> subclass = subobj.getClass();
			ParameterizedType ptype = null;
			while (!supclass.equals(subclass)) {
				System.out.println("get-"+subclass);
				Type type = subclass.getGenericSuperclass();
				if (type == null) {
					System.out.println("end null");
					return null;
				}else if (type instanceof Class) {
					subclass = (Class<?>)type;
				}else if (type instanceof ParameterizedType) {
					ptype = ParameterizedType.class.cast(type);
					subclass = (Class<?>)ptype.getRawType();
					System.out.println("get ParameterizedType");
				}else if (type instanceof GenericArrayType) {
					GenericArrayType gtype = GenericArrayType.class.cast(type);
					subclass = (Class<?>)gtype.getGenericComponentType();
					System.out.println("get GenericArrayType");
				}else{
					System.out.println("end else");
					return null;
				}
			}
			System.out.println("find-"+subclass);
			return (Class<?>)ptype.getActualTypeArguments()[index];
		}

		/**
		 * 获取 subobj 相对 父类 supclass 的 第index个泛型参数
		 * @param subobj 对象 一般用this
		 * @param supclass 父类 一般用自己的类class
		 * @param index 要获取参数的序列 一般用0
		 * @return 返回参数类型Class<?> 
		 * @Error 初始化失败（检查参数是否传正确）
		 */
		public Class<?> getActualTypeArgumentWidthError(Object subobj,Class<?> supclass,int index){
			Class<?> pclass = getActualTypeArgument(subobj, supclass, index);
			if (pclass == null) {
				throw new Error("初始化"+subobj.getClass()+"泛型参数失败！");
			}
			return pclass;
		}

		public void outTypeParameters(Class<?> subclass) {
			// TODO Auto-generated method stub
			TypeVariable<?>[] types = subclass.getTypeParameters();
			for (TypeVariable<?> typeVariable : types) {
				System.out.println("name="+typeVariable.getName());
				Type[] bounds = typeVariable.getBounds();
				for (Type type : bounds) {
					System.out.println("value="+type);
				}
			}
		}
	}
	public static class SubClass extends Base<String,Thread>{
		
	}
	public static class SubClass1<TT> extends SubClass{
		
	}
	public static class SubClass2 extends SubClass1<Integer>{
		
	}

	@Test
	public void testClassParamType() throws Exception {
//		new SubClass();
//		new SubClass2();
		System.out.println(System.out+" - "+System.out.getClass());
		new Base<Integer,SubClass>(){};
	}

	@Test
	public void testGenericSuperclass() throws Exception {
		//获取上一层直接父类
		Type gs = SubClass.class.getGenericSuperclass();
		System.out.println(gs);
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void testSubclass() throws Exception {
		Class<? extends Base> clazz = SubClass.class.asSubclass(Base.class);
		System.out.println(clazz);
		Class<? extends SubClass> clazs = clazz.asSubclass(SubClass.class);
		System.out.println(clazs);
//		Base obj = clazz.cast(clazz);
	}


	@Test
    public void testMapParams() throws Exception {   
        // 获取Class实例   
        Class<?> class1 = ClassTypeTest.class;   
        // 根据属性名取得该属性对应的Field对象   
        Field mapField = class1.getDeclaredField("map");   
        // 示范第一个方法：直接通过getType()取出Field的类型，只对普通类型的Field有效   
        Class<?> class2 = mapField.getType();   
        // 输出查看   
        System.out.println("属性名为map的属性类型为："+class2);   
           
        // 示范第二种方法：   
        Type mapMainType = mapField.getGenericType();   
        // 为了确保安全转换，使用instanceof   
        if (mapMainType instanceof ParameterizedType) {   
            // 执行强制类型转换   
            ParameterizedType parameterizedType = (ParameterizedType)mapMainType;   
            // 获取基本类型信息，即Map   
            Type basicType = parameterizedType.getRawType();   
            System.out.println("基本类型为："+basicType);   
            // 获取泛型类型的泛型参数   
            Type[] types = parameterizedType.getActualTypeArguments();   
            for (int i = 0; i < types.length; i++) {   
                System.out.println("第"+(i+1)+"个泛型类型是："+types[i]);   
            }   
        } else {   
            System.out.println("获取泛型类型出错!");   
        }   
    }   
	
//	/**
//	 * 两个方法重载不成立
//	 */
//	public static void test(List<Integer> testParameter) {  
//	      
//	}  
//	  
//	public static void test(List<String> testParameter) {  
//	  
//	}  
}
