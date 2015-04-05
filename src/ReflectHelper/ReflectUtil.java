package ReflectHelper;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

	/**
	 * @Description: ��ȡ subobj ��� ���� supclass �� ��index�����Ͳ���
	 * @param subobj ���� һ����this �� class Type<E> �ɴ��� Type.this 
	 * @param supclass ����(ģ����) �� class Type<E> �ɴ��� Type.class 
	 * @param index Ҫ��ȡ���������� һ����0
	 * @return null ����ʧ�� ���򷵻ز�������Class<?>
	 */
	public static Class<?> getActualTypeArgument(Object subobj,Class<?> supclass,int index){
		Class<?> subclass = subobj.getClass();
		ParameterizedType ptype = null;
		while (!supclass.equals(subclass)) {
			Type type = subclass.getGenericSuperclass();
			if (type == null) {
				throw new Error("GenericSuperclass not find");
			}else if (type instanceof Class) {
				subclass = (Class<?>)type;
			}else if (type instanceof ParameterizedType) {
				ptype = ParameterizedType.class.cast(type);
				subclass = (Class<?>)ptype.getRawType();
			}else if (type instanceof GenericArrayType) {
				GenericArrayType gtype = GenericArrayType.class.cast(type);
				subclass = (Class<?>)gtype.getGenericComponentType();
			}else{
				throw new Error("GenericSuperclass not case");
			}
		}
		return (Class<?>)ptype.getActualTypeArguments()[index];
	}
	
	/**
	 * ���÷������� ��ȡtype��field
	 * @param type
	 * @param field ��֧�֡�.��·��
	 * @return field or null
	 */
	public static Field getField(Class<?> type, String field)
	{
		// TODO Auto-generated method stub
		while (!type.equals(Object.class))
		{
			try
			{
				return type.getDeclaredField(field);
			} catch (NoSuchFieldException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			type = type.getSuperclass();
		}
		return null;
	}
	
	/**
	 * ���÷������� ����ȡ����type������path��Field
	 * @param obj
	 * @param type
	 * @param path
	 * @param index
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws Exception ����Խ��
	 */
	private static Field getField(Class<?> type, String[] path,int index) throws Exception{
		Field field = getField(type,path[index]);
		if (path.length == index + 1) {
			return field;
		} else if (path.length > 0) {
			return getField(field.getType(), path, index + 1);
		}
		return field;
	}

	/**
	 * ��ȡ�ֶ� Field
	 * @param model 
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws Exception
	 */
	public static Field getField(Object model, String field) throws Exception {
		// TODO Auto-generated method stub
		return getField(model.getClass(), field.split("\\."), 0);
	}

	/**
	 * 
	 * @param model
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 */
	public static Field getFieldNoException(Object model, String field) {
		// TODO Auto-generated method stub
		try {
			return getField(model.getClass(), field.split("\\."), 0);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	/**
	 * ���÷������� ����obj������pathΪ value
	 * @param type
	 * @param path
	 * @param obj
	 * @param value
	 * @param index ����ָ��Ϊ 0
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static void invokeMember(Class<?> type, String[] path, Object obj,
			Object value, int index) throws Exception {
		Field field = getField(type,path[index]);
		if (path.length == index + 1) {
			field.setAccessible(true);
			field.set(obj, value);
		} else if (path.length > 0) {
			value = field.get(obj);
			invokeMember(obj.getClass(), path, obj, value, index + 1);
		}
	}

	/**
	 * ���÷������� ��ȡ����obj������path��value
	 * @param obj
	 * @param type
	 * @param path
	 * @param index
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	private static Object invokeMember(Class<?> type, String[] path,Object obj, 
			int index) throws Exception  {
		Field field = getField(type,path[index]);
		field.setAccessible(true);
		Object value = field.get(obj);
		if (path.length == index + 1) {
			return value;
		} else if (path.length > 0 && value != null) {
			return invokeMember(value.getClass(), path, value, index + 1);
		}
		return value;
	}

	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static void setMember(Object obj,String field,Object value) throws Exception {
		invokeMember(obj.getClass(), field.split("\\."), obj,value, 0);
	}

	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static boolean setMemberNoException(Object obj,String field,Object value) {
		try {
			invokeMember(obj.getClass(), field.split("\\."), obj,value, 0);
			return true;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			return false;
		}
	}


	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static Object getMember(Object obj,String field) throws Exception {
		return invokeMember(obj.getClass(), field.split("\\."), obj, 0);
	}

	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static Object getMemberNoException(Object obj,String field) {
		try {
			return invokeMember(obj.getClass(), field.split("\\."), obj, 0);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static <T> T getMember(Object obj,String field,Class<T> clazz) throws Exception {
		obj = invokeMember(obj.getClass(), field.split("\\."), obj, 0);
		if(clazz.isInstance(obj)){
			clazz.cast(obj);
		}
		return null;
	}

	/**
	 * ��ȡ obj ���� field ��ֵ
	 * @param obj
	 * @param field ֧�֡�.��·�� �� person.name
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception ����Խ��
	 */
	public static <T> T  getMemberNoException(Object obj,String field,Class<T> clazz) {
		try {
			return clazz.cast(invokeMember(obj.getClass(), field.split("\\."), obj, 0));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
