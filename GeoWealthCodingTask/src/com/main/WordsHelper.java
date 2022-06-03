package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author bborisov
 */
public class WordsHelper {

	static Logger logger = Logger.getLogger(WordsHelper.class.getName());

	/**
	 * @return List of all read words
	 * @throws IOException
	 */
	public List<String> loadAllWords() throws IOException {
		URL wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(wordsUrl.openConnection().getInputStream()))) {
			return br.lines().skip(2).collect(Collectors.toList());
		}
	}

	/**
	 * @param words
	 * @return List with reduced words. All biggest than nine characters words, words that do not contains "i" or "a" will be removed. 
	 */
	public List<String> removeAllBiggestWord(List<String> words) {
		words.removeIf(w -> w.length() > 9 || (!w.toLowerCase().contains("i") && !w.toLowerCase().contains("a")));
		return words;
	}

	/**
	 * @param words
	 * @return WordsListDto
	 */
	public WordsListsDto getWordsListsDto(List<String> words) {
		WordsListsDto wordsListsDto = new WordsListsDto();
		for (String word : words) {
			if (word.length() == 9) {
				wordsListsDto.getNineCharsWords().add(word);
			} else {
				wordsListsDto.getSmallerThenNineWords().add(word);
			}
		}
		wordsListsDto.getSmallerThenNineWords().add("I");
		wordsListsDto.getSmallerThenNineWords().add("A");
		return wordsListsDto;
	}

	/**
	 * @return HashSet with all founded words which can fall apart. Empty if there is no words.
	 */
	public Set<String> getCorrectWords() {
		Set<String> foundedWordsSet = new HashSet<>();
		try {
			List<String> readedWords = loadAllWords();
			readedWords = removeAllBiggestWord(readedWords);
			WordsListsDto wordsListsDto = getWordsListsDto(readedWords);
			readedWords = null;
			for (String nineCharsWord : wordsListsDto.getNineCharsWords()) {
				boolean wordFounded = getRecursiveWords(wordsListsDto.getSmallerThenNineWords(), nineCharsWord);
				if (wordFounded) {
					foundedWordsSet.add(nineCharsWord);
				}
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		return foundedWordsSet;
	}

	
	/**
	 * @param smallerThenNineWords
	 * @param word
	 * @return true if the word can fall apart, else return false.
	 */
	private boolean getRecursiveWords(List<String> smallerThenNineWords, String word) {
		if (word.length() < 9 && !smallerThenNineWords.contains(word)) {
			return false;
		}
		if (word.length() == 1) {
			return true;
		}
		for (int i = 0; i < word.length(); i++) {
			String newWord = word.substring(0, i) + word.substring(i + 1, word.length());
			if (smallerThenNineWords.contains(newWord)) {
				if (getRecursiveWords(smallerThenNineWords, newWord)) {
					System.out.println(newWord);
					return true;
				}
			}
		}
		return false;
	}

}
