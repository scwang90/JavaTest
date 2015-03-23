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
		 * @Description: ��ȡ subobj ��� ���� supclass �� ��index�����Ͳ���
		 * @param subobj ���� һ����this
		 * @param supclass ���� һ�����Լ�����class
		 * @param index Ҫ��ȡ���������� һ����0
		 * @return null ����ʧ�� ���򷵻ز�������Class<?>
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
		 * @Description: ��ȡ subobj ��� ���� supclass �� ��index�����Ͳ���
		 * @param subobj ���� һ����this
		 * @param supclass ���� һ�����Լ�����class
		 * @param index Ҫ��ȡ���������� һ����0
		 * @return ���ز�������Class<?> 
		 * @Error ��ʼ��ʧ�ܣ��������Ƿ���ȷ��
		 */
		public Class<?> getActualTypeArgumentWidthError(Object subobj,Class<?> supclass,int index){
			Class<?> pclass = getActualTypeArgument(subobj, supclass, index);
			if (pclass == null) {
				throw new Error("��ʼ��"+subobj.getClass()+"���Ͳ���ʧ�ܣ�");
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
		//��ȡ��һ��ֱ�Ӹ���
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
        // ��ȡClassʵ��   
        Class<?> class1 = ClassTypeTest.class;   
        // ����������ȡ�ø����Զ�Ӧ��Field����   
        Field mapField = class1.getDeclaredField("map");   
        // ʾ����һ��������ֱ��ͨ��getType()ȡ��Field�����ͣ�ֻ����ͨ���͵�Field��Ч   
        Class<?> class2 = mapField.getType();   
        // ����鿴   
        System.out.println("������Ϊmap����������Ϊ��"+class2);   
           
        // ʾ���ڶ��ַ�����   
        Type mapMainType = mapField.getGenericType();   
        // Ϊ��ȷ����ȫת����ʹ��instanceof   
        if (mapMainType instanceof ParameterizedType) {   
            // ִ��ǿ������ת��   
            ParameterizedType parameterizedType = (ParameterizedType)mapMainType;   
            // ��ȡ����������Ϣ����Map   
            Type basicType = parameterizedType.getRawType();   
            System.out.println("��������Ϊ��"+basicType);   
            // ��ȡ�������͵ķ��Ͳ���   
            Type[] types = parameterizedType.getActualTypeArguments();   
            for (int i = 0; i < types.length; i++) {   
                System.out.println("��"+(i+1)+"�����������ǣ�"+types[i]);   
            }   
        } else {   
            System.out.println("��ȡ�������ͳ���!");   
        }   
    }   
	
//	/**
//	 * �����������ز�����
//	 */
//	public static void test(List<Integer> testParameter) {  
//	      
//	}  
//	  
//	public static void test(List<String> testParameter) {  
//	  
//	}  
}
