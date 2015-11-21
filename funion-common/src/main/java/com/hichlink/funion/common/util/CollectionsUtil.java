package com.hichlink.funion.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

public class CollectionsUtil {
	private CollectionsUtil(){}
	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Collection collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Map map) {
		return (map == null) || map.isEmpty();
	}
	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection<T> collection) {
		return (collection != null) && !(collection.isEmpty());
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	@SuppressWarnings("hiding")
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}
	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("aaaa");
		System.out.println(getFirst(list));
	}
}
