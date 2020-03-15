package com.streamlinity.ct.springRestChallenge.solution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.streamlinity.ct.springRestChallenge.api.Item;

/*
 * This controller needs to expose the following rest endpoints.  You need to fill in the implementation here
 *
 * Required REST Endpoints
 *
 *      /item                       Get all items
 *      /item?category=C            Get all items in category specified by Category shortName
 *      /item/{itemShortName}       Get item that matches the specified Item shortName
 */

@Profile("default")
@RestController
public class SearchRestControllerImpl {
	
	@Autowired
	SearchSvcImpl searchSvcImpl;
	
	@GetMapping("/item")
	@ResponseBody
	public List<Item> getAllItems() {
		
		searchSvcImpl.init("itemPrices.json");
		searchSvcImpl.getItems();
		
		return searchSvcImpl.getItemList();
	}
	
	@GetMapping("/item")
	@ResponseBody
	public List<Item> getItemsForCategory(@PathVariable("category") String category) {
		return searchSvcImpl.getItems(category);
	}
	
	@GetMapping("/item/{itemShortName}")
	@ResponseBody
	public List<Item> getItemsForItemShortName(@PathVariable("itemShortName") String itemShortName) {
		return searchSvcImpl.getItems(itemShortName);
	}
}
