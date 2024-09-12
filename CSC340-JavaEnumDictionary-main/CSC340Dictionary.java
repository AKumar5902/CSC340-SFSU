
import java.util.*;

enum Dictionary {
	Arrow("Arrow", "noun", "Here is one arrow: <IMG> -=>> </IMG>"), Book("Book", "noun", "A set of pages"),
	Book1("Book", "noun", "A written work published in printed or electronic form"),
	Book2("Book", "verb", "To arrange for someone to have a seat on a plan"),
	Book3("Book", "verb", "To arrange something on a particular date."),
	Distinct("Distinct", "adjective", "Familiar. Worked in Java"),
	Distinct1("Distinct", "adjective", "Unique. No duplicates. Clearly different or of a different kind."),
	Distinct2("Distinct", "adverb", "Uniquely. Written \"distinctly"),
	Distinct3("Distinct", "noun", "A keyword in this assignment."),
	Distinct4("Distinct", "noun", "A keyword in this assignment."),
	Distinct5("Distinct", "noun", "A keyword in this assignment."),
	Distinct6("Distinct", "noun", "An advanced search option."),
	Distinct7("Distinct", "noun", "Distinct is a parameter in this assignment."),
	Placeholder("Placeholder", "adjective", "To be updated..."),
	Placeholder1("Placeholder", "adjective", "To be updated..."),
	Placeholder2("Placeholder", "adverb", "To be updated..."),
	Placeholder3("Placeholder", "conjuction", "To be updated..."),
	Placeholder4("Placeholder", "interjection", "To be updated..."),
	Placeholder5("Placeholder", "noun", "To be updated..."), Placeholder6("Placeholder", "noun", "To be updated..."),
	Placeholder7("Placeholder", "noun", "To be updated..."),
	Placeholder8("Placeholder", "preposition", "To be updated..."),
	Placeholder9("Placeholder", "pronoun", "To be updated..."),
	Placeholder10("Placeholder", "verb", "To be updated..."), Reverse("Reverse", "adjective", "On back side"),
	Reverse1("Reverse", "adjective", "Opposite to usual or previous arrangement."),
	Reverse2("Reverse", "noun", "A dictionary program's parameter."),
	Reverse3("Reverse", "noun", "Change to opposite direction"), Reverse4("Reverse", "noun", "The opposite"),
	Reverse5("Reverse", "noun", "To be updated..."), Reverse6("Reverse", "noun", "To be updated..."),
	Reverse7("Reverse", "noun", "To be updated..."), Reverse8("Reverse", "noun", "To be updated..."),
	Reverse9("Reverse", "verb", "Change something to opposite "), Reverse10("Reverse", "verb", "Go back"),
	Reverse11("Reverse", "verb", "Revoke Ruling"), Reverse12("Reverse", "verb", "To be updated..."),
	Reverse13("Reverse", "verb", "To be updated..."), Reverse14("Reverse", "verb", "Turn something inside out"),
	Hello("He","noun","Greeting"),
	Bye("Bye","noun","Greeting"),Hello2("Hel","noun","Greeting"),Hello3("HelL","noun","Greeting"),
	Hello1("H","noun","Greeting"),Hello5("Hello","noun","Greeting"),Bye1("B","noun","Greeting"),
	Bye3("By","noun","Greeting"),Goodnight("Goodnight","noun","Sleep"),Goodnight1("Good","noun","Sleep"),
	Goodnight2("Goodn","noun","Sleep"),Goodnight3("Goodni","noun","Sleep"),Goodnight4("Goodnig","noun","Sleep"),
	Goodnight5("Goodnigh","noun","Sleep"),Goodnight6("Goodnigh","noun","Sleep"),Goodnight7("Goodnigh","noun","Sleep"),
	Goodnight8("Goodnigh","noun","Sleep"),Goodnight9("Goodnigh","noun","Sleep"),Goodnight10("Goodnigh","noun","Sleep"),
	Bye4("By","noun","Greeting"),Bye5("By","noun","Greeting"),Bye6("By","noun","Greeting")
	

	;

	private String keyword;
	private String partOfSpeech;
	private String definition;

	private Dictionary(String keyword, String partOfSpeech, String definition) {
		this.keyword = keyword;
		this.partOfSpeech = partOfSpeech;
		this.definition = definition;
	}

	public String getKeyword() {
		return this.keyword.toUpperCase();
	}

	public String getPartOfSpeech() {
		return this.partOfSpeech;
	}

	public String getDefinition() {
		return this.definition;
	}

	public int hash() {
		return Objects.hash(keyword, partOfSpeech, definition);
	}

	@Override
	public String toString() {
		return " " + this.keyword + " " + "[" + this.partOfSpeech + "]" + " " + ":" + " " + this.definition;
	}

}

public class CSC340Dictionary implements Comparator<Dictionary> {
	private static HashMap<String, ArrayList<Dictionary>> hmap = new HashMap<String, ArrayList<Dictionary>>();
	private static String[] partOfSpeeches = { "noun", "verb", "adjective", "adverb", "pronoun", "preposition",
			"conjunction", "interjection" };
	private static String word = null;
	private static Boolean distinct = false;
	private static String partOfSpeech = null;
	private static Boolean reverse = false;
	private static int count = 1;

	public static HashMap<String, ArrayList<Dictionary>> loadData() {
		System.out.println("! Loading Data...");
int def=0;
		for (Dictionary dict : Dictionary.values()) {

			String keyword = dict.getKeyword();
			

			ArrayList<Dictionary> list = (hmap.containsKey(keyword)) ? hmap.get(keyword) : new ArrayList<Dictionary>();
			list.add(dict);
			def++;
			hmap.put(keyword, list);
			

		}
		System.out.println("! Loading Completed... \n");
		System.out.println("=====DICTIONARY 340 JAVA=====");
		System.out.println("----- Keywords: "+hmap.size());
		System.out.println("----- Definitions: "+def+"\n");

		return hmap;
	}

	@Override
	public int compare(Dictionary o1, Dictionary o2) {
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;

		int keywordComparison = (o1).getKeyword().compareTo((o2).getKeyword());
		if (keywordComparison != 0)
			return keywordComparison;

		int partOfSpeechComparison = (o1).getPartOfSpeech().compareTo((o2).getPartOfSpeech());
		if (partOfSpeechComparison != 0)
			return partOfSpeechComparison;
		return (o1).getDefinition().compareTo((o2).getDefinition());
	}

	public static ArrayList<Dictionary> search(String key) {
		ArrayList<Dictionary> result = hmap.get(key.toUpperCase());
		if (reverse == true) {
			return reverse(result);
		} else {
			return result;
		}
	}

	public static ArrayList<Dictionary> searchDistinct(ArrayList<Dictionary> list) {
		HashMap<String, Dictionary> hMap = new HashMap<String, Dictionary>();
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();

		if (list != null) {
			for (Dictionary d : list) {
				String def = Integer.toString(d.hash());

				if (!hMap.containsKey(def)) {
					hMap.put(def, d);
				}
			}
		}
		result = new ArrayList<Dictionary>(hMap.values());
		//Collections.sort(result, new CSC340Dictionary());
		if (reverse == true) {
			return reverse(result);
		} else {

			return result;
		}
	}

	public static ArrayList<Dictionary> searchPartOfSpeech(ArrayList<Dictionary> list, String p) {
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();
		if (list != null) {
			for (Dictionary d : list) {
				if (d.getPartOfSpeech().equalsIgnoreCase(p)) {
					result.add(d);
				}
			}
		}
		Collections.sort(result, new CSC340Dictionary());
		if (reverse == true) {
			return reverse(result);
		} else {
			return result;
		}
	}

	public static ArrayList<Dictionary> reverse(ArrayList<Dictionary> list) {
		ArrayList<Dictionary> reverse = new ArrayList<Dictionary>();
		for (int i = list.size() - 1; i >= 0; i--) {
			reverse.add(list.get(i));
		}
		return reverse;
	}

	public static ArrayList<Dictionary> processOneP(String input) {
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();
		String[] splitted = input.split(" ");
		String word = splitted[0];
		if (search(splitted[0]) != null) {
			result = search(word);
		} else {
			notFoundMessage();
			help();
		}
		return result;
	}

	public static ArrayList<Dictionary> processTwoP(String input) {
		String[] splitted = input.split(" ");
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();
		String word = splitted[0];
		result = processOneP(input);
		if (distinct == true) {
			result = searchDistinct(search(word));
		}
		if (splitted[1].equalsIgnoreCase("reverse")) {
			reverse = true;
			result = reverse(result);

		} else if (splitted[1].equalsIgnoreCase("distinct")) {
			distinct = true;
			result = searchDistinct(search(word));

		} else if (Arrays.asList(partOfSpeeches).contains(splitted[1].toLowerCase())) {
			partOfSpeech = splitted[1];
			if (reverse == true) {
				result = reverse(searchPartOfSpeech(result, partOfSpeech));
				Collections.sort(result, new CSC340Dictionary());
				result = reverse(result);
			} else {
				result = searchPartOfSpeech(result, partOfSpeech);
			}
			if (result.isEmpty()) {
				notFoundMessage();
				help();
			}
		} else {
			message(splitted[1]);
		}
		return result;
	}

	public static ArrayList<Dictionary> processThreeP(String input) {
		String[] splitted = input.split(" ");
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();
		if (splitted[2].equalsIgnoreCase("reverse")) {
			reverse = true;
			result = processTwoP(input);
		} else if (splitted[2].equalsIgnoreCase("distinct")) {
			distinct = true;
			result = processTwoP(input);
		} else {
			result = processTwoP(input);
			messageThridP(splitted[2]);
		}
		return result;
	}

	public static ArrayList<Dictionary> processFourP(String input) {
		String[] splitted = input.split(" ");
		ArrayList<Dictionary> result = new ArrayList<Dictionary>();
		if (splitted[3].equalsIgnoreCase("reverse")) {
			reverse = true;
			result = processThreeP(input);
		} else {
			result = processThreeP(input);
			messageFourthP(splitted[3]);
		}
		return result;
	}

	public static void notFoundMessage() {
		System.out.println(" |\n  <NOT FOUND> To be considered for the next release. Thank you." + "\n |");
	}

	public static void help() {
		System.out.println(
				" |\n  PARAMETER HOW-TO, please enter:\n" + "  1. A search key -then 2. An optional part of speech -then\n"
						+ "  3. An optional 'distinct' -then 4. An optional 'reverse'" + "\n |");
	}

	public static void message(String error) {
		System.out.println(" |\n  <The entered 2nd parameter '" + error + "' is NOT a part of speech.>\n"
				+ "  <The entered 2nd parameter '" + error + "' is NOT 'distinct'.>\n" + "  <The entered 2nd parameter '"
				+ error + "' is NOT 'reverse'.>\n" + "  <The entered 2nd parameter '" + error + "' was disregarded.>\n"
				+ "  <The 2nd parameter should be a part of speech or 'distinct' or 'reverse'.>" + "\n |");
	}

	public static void messageThridP(String error) {
		System.out.println(" |\n  <The entered 3rd parameter '" + error + "' is NOT 'distinct'.>\n"
				+ "  <The entered 3rd parameter '" + error + "' is NOT 'reverse'.>\n" + "  <The entered 3rd parameter '"
				+ error + "' was disregarded.>\n" + "  <The 3rd parameter should be'distinct' or 'reverse'.>" + "\n |"
				);
	}

	public static void messageFourthP(String error) {
		System.out.println(" |\n  <The entered 4th parameter '" + error + "' is NOT 'reverse'.>\n"
				+ "  <The entered 4th parameter '" + error + "' was disregarded.>\n"
				+ "  <The 4th parameter should be 'reverse'.>" + "\n |");

	}

	public static void start() {
		loadData();
		Scanner scan = new Scanner(System.in);
		Boolean quit = true;

		do {
			ArrayList<Dictionary> result = new ArrayList<Dictionary>();
			reverse = false;
			distinct = false;

			System.out.print("Search [" + count + "] : ");
			String input = scan.nextLine();

			String[] splitted = input.split(" ");
			if (splitted[0].equalsIgnoreCase("!help") || splitted[0].equalsIgnoreCase("")) {
				help();
			} else if (splitted[0].equalsIgnoreCase("!q")) {
				System.out.println("\n----Thank You---");
				quit = false;
			} else if (splitted.length == 1) {
				result = processOneP(input);
			} else if (splitted.length == 2) {
				result = processTwoP(input);
			} else if (splitted.length == 3) {
				result = processThreeP(input);
			} else if (splitted.length == 4) {
				result = processFourP(input);
			} else if (splitted.length > 4) {
				help();
			}
			if(!result.isEmpty()) {
				System.out.println(" |");
			for (Dictionary entry : result) {
				System.out.println(" "+entry);
			}
			System.out.println(" |");
			}
			

			count++;
		} while (quit);
		scan.close();
	}

	public static void main(String[] args) {
		start();
	}

}