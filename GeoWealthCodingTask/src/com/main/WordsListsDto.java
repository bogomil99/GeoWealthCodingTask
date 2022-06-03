package com.main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bborisov
 */
public class WordsListsDto {

	List<String> nineCharsWords;
	List<String> smallerThenNineWords;

	public WordsListsDto() {
		nineCharsWords = new ArrayList<>();
		smallerThenNineWords = new ArrayList<>();
	}

	public List<String> getNineCharsWords() {
		return nineCharsWords;
	}
	
	public List<String> getSmallerThenNineWords() {
		return smallerThenNineWords;
	}

}
