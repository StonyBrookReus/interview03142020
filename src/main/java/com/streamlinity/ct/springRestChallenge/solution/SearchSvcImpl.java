package com.streamlinity.ct.springRestChallenge.solution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamlinity.ct.springRestChallenge.api.Item;
import com.streamlinity.ct.springRestChallenge.api.SearchSvcInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

/*
 * Provide your implementation of the SearchSvcImpl here.
 * Also annotate your methods with Rest end point wrappers as required in the problem statement
 *
 * You can create any auxiliary classes or interfaces in this package if you need them.
 *
 * Do NOT add annotations as a Bean or Component or Service.   This is being handled in the custom Config class
 * PriceAdjustConfiguration
 */

public class SearchSvcImpl implements SearchSvcInterface {

	private JSONArray allItemsJson;
	private List<Item> itemList;

	@Override
	public void init(String itemPriceJsonFileName) {

		File inputFile = new File(getClass().getClassLoader().getResource(itemPriceJsonFileName).getFile());

		try {
			InputStream is = new FileInputStream(inputFile.getPath());
			String allItemsString = IOUtils.toString(is);
			allItemsJson = new JSONArray(allItemsString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(File itemPriceJsonFile) {

		try {
			InputStream is = new FileInputStream(itemPriceJsonFile.getPath());
			String allItemsString = IOUtils.toString(is);
			allItemsJson = new JSONArray(allItemsString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Item> getItems() {

		ObjectMapper mapper = new ObjectMapper();
		int size = allItemsJson.length();
		itemList = new ArrayList<>();

		try {
			for (int i = 0; i < size; i++) {
				itemList.add(mapper.readValue(allItemsJson.getJSONObject(i).toString(), Item.class));
			}
		} catch (JsonProcessingException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemList;
	}

	@Override
	public List<Item> getItems(String category) {
		List<Item> newItemListForCategory = itemList.stream().filter(item -> {
			return item.getCategory_short_name().equals(category);
		}).collect(Collectors.toList());
		
		return newItemListForCategory;
	}

	@Override
	public List<Item> getItem(String itemShortName) {
		List<Item> newItemListForItemShortName = itemList.stream().filter(item -> {
			return item.getShort_name().equals(itemShortName);
		}).collect(Collectors.toList());
		
		return null;
	}

	public List<Item> getItemList() {
		return itemList;
	}
	
	
}
