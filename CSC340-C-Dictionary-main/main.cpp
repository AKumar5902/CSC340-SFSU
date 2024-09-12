#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <functional>
#include <cctype>
#include <set>

using namespace std;
struct Dictionary {
    string key;
    string partOfSpeech;
    string definition;
};
unordered_map<string, vector<Dictionary>> dictionary;

unordered_map<string, vector<Dictionary>> loadData(string filePath);

bool reversed = false;
string partOfSpeech;
int searchNum = 1;
vector<string> partOfSpeeches = {"noun", "verb", "adjective", "adverb", "pronoun", "preposition",
                                 "conjunction", "interjection"};

void notFoundMessage() {
    cout << " \t   |\n  \t\t<NOT FOUND> To be considered for the next release. Thank you." << "\n \t   |" << endl;
}

void help() {
    cout << "\t   |\n  \t\tPARAMETER HOW-TO, please enter:\n"
         << "\t\t1. A search key -then 2. An optional part of speech -then\n"
         << "\t\t3. An optional 'distinct' -then 4. An optional 'reverse'"
         << "\n \t   |" << endl;
}

vector<string> split(string &s) {
    vector<string> params;
    stringstream ss(s);
    string param;
    while (ss >> param) {
        transform(param.begin(), param.end(), param.begin(), ::tolower);
        params.push_back(param);
    }
    return params;
}

bool compareDictionaries(Dictionary &d1, Dictionary &d2) {
    if (d1.key != d2.key) {
        return d1.key < d2.key;
    }

    if (d1.partOfSpeech != d2.partOfSpeech) {
        return d1.partOfSpeech < d2.partOfSpeech;
    }

    return d1.definition < d2.definition;
}

vector<Dictionary> search(string &key) {
    string uppercaseKey = key;
    transform(uppercaseKey.begin(), uppercaseKey.end(), uppercaseKey.begin(), ::toupper);
    vector<Dictionary> result;
    auto it = dictionary.find(key);
    if (it != dictionary.end()) {
        result = it->second;
        sort(result.begin(), result.end(), compareDictionaries);
    }
    return result;
}

bool equalIgnoreCase(const string &s1, const string &s2) {
    if (s1.size() != s2.size()) {
        return false;
    }
    return equal(s1.begin(), s1.end(), s2.begin(), [](char c1, char c2) {
        return tolower(c1) == tolower(c2);
    });
}

vector<Dictionary> searchPartOfSpeech(const vector<Dictionary> &list, const string &p) {
    vector<Dictionary> result;

    for (const auto &d: list) {
        if (equalIgnoreCase(d.partOfSpeech, p)) {
            result.push_back(d);
        }
    }

    return result;
}

vector<Dictionary> searchDistinct(const vector<Dictionary> &list) {
    vector<Dictionary> result;
    set<string> seenDefinitions;
    for (const auto &dict: list) {
        string combinedKey = dict.partOfSpeech + ":" + dict.definition;
        auto insertionResult = seenDefinitions.insert(combinedKey);
        if (insertionResult.second) {
            result.push_back(dict);
        }
    }
    sort(result.begin(), result.end(), compareDictionaries);

    return result;
}

vector<Dictionary> processOneP(string &input) {
    vector<Dictionary> result;
    vector<string> splitted = split(input);
    string word = splitted[0];
    result = search(word);
    if (!result.empty()) {
        result = result;
    } else {
        notFoundMessage();
        help();
    }

    return result;
}

vector<Dictionary> processTwoP(string &input, bool distinct) {
    vector<Dictionary> result;
    vector<string> splitted = split(input);
    string word = splitted[0];
    result = processOneP(input);
    if (distinct) {
        result = searchDistinct(search(word));
    }
    if (equalIgnoreCase(splitted[1], "reverse")) {
        reversed = true;
        processOneP(input);
        reverse(result.begin(), result.end());
    } else if (equalIgnoreCase(splitted[1], "distinct")) {
        result = searchDistinct(search(word));
        if (reversed) {
            reverse(result.begin(), result.end());
        }
    } else if (find(partOfSpeeches.begin(), partOfSpeeches.end(), splitted[1]) != partOfSpeeches.end()) {
        partOfSpeech = splitted[1];
        if (reversed) {
            result = (searchPartOfSpeech(result, partOfSpeech));
            reverse(result.begin(), result.end());
        } else {
            result = searchPartOfSpeech(result, partOfSpeech);
        }
    } else {
        cout << " \t   |\n  \t\t<The entered 2nd parameter '" << splitted[1] << "' is NOT a part of speech.>\n"
             << "\t\t<The entered 2nd parameter '" << splitted[1] << "' is NOT 'distinct'.>\n"
             << "\t\t<The entered 2nd parameter '" << splitted[1] << "' is NOT 'reverse'.>\n"
             << "\t\t<The entered 2nd parameter '" << splitted[1] << "' was disregarded.>\n"
             << "\t\t<The 2nd parameter should be a part of speech or 'distinct' or 'reverse'.>\n"
             << " \t   |" << endl;
    }
    if (result.empty()) {
        notFoundMessage();
        help();
    }

    return result;
}

vector<Dictionary> processThreeP(string &input) {
    vector<Dictionary> result;
    vector<string> splitted = split(input);
    if (equalIgnoreCase(splitted[2], "reverse")) {
        reversed = true;
        result = processTwoP(input, false);
    } else if (equalIgnoreCase(splitted[2], "distinct")) {
        result = processTwoP(input, true);
    } else {
        result = processTwoP(input, false);
        cout << " \t   |\n  \t\t<The entered 3rd parameter '" << splitted[2] << "' is NOT 'distinct'.>\n"
             << "\t\t<The entered 3rd parameter '" << splitted[2] << "' is NOT 'reverse'.>\n"
             << "\t\t<The entered 3rd parameter '" << splitted[2] << "' was disregarded.>\n"
             << "\t\t<The 3rd parameter should be'distinct' or 'reverse'.>\n"
             << "\t   |" << endl;
    }
    return result;
}

vector<Dictionary> processFourP(string &input) {
    vector<Dictionary> result;
    vector<string> splitted = split(input);
    if (equalIgnoreCase(splitted[3], "reverse")) {
        reversed = true;
        result = processThreeP(input);
    } else {
        result = processThreeP(input);
        cout << " \t   |\n  \t\t<The entered 4th parameter '" << splitted[3] << "' is NOT 'reverse'.>\n"
             << " \t\t<The entered 4th parameter '" << splitted[3] << "' was disregarded.>\n"
             << " \t\t<The 4th parameter should be 'reverse'.>\n"
             << " \t   |" << endl;
    }
    return result;
}

void start() {
    loadData(" C:\\Users\\MickeyMouse\\AbsolutePath\\DB\\Data.CS.SFSU.txt");
    bool quit = true;
    do {
        reversed = false;
        vector<Dictionary> result;
        cout <<"Search [" << searchNum << "]: ";
        string input;
        getline(cin, input);
        vector<string> splitted = split(input);
        if (input.empty() || input == "") {
            help();
        } else if (splitted[0] == "!help" || splitted[0].empty()) {
            help();
        } else if (splitted[0] == ("!q")) {
            cout << ("\n----Thank You---") << endl;
            quit = false;
        } else if (splitted.size() == 1) {
            result = processOneP(input);
        } else if (splitted.size() == 2) {
            result = processTwoP(input, false);
        } else if (splitted.size() == 3) {
            result = processThreeP(input);
        } else if (splitted.size() == 4) {
            result = processFourP(input);
        } else if (splitted.size() >= 5) {
            help();
        }
        if (!result.empty()) {
            cout << "       |" << endl;
            for (const auto &dict: result) {
                string capKey = dict.key;
                if (!capKey.empty()) {
                    capKey[0] = toupper(capKey[0]);
                }
                if (capKey[0] == 'C') {
                    transform(capKey.begin(), capKey.end(), capKey.begin(), ::toupper);
                }
                cout << "        " << capKey << " [" << dict.partOfSpeech << "] : " << dict.definition << endl;
            }
            cout << ("       |") << endl;
        }
        result.clear();
        searchNum++;
    } while (quit);

}

int main() {
    start();
    return 0;
}

void processFile(ifstream &file) {
    string line;

    while (getline(file >> ws, line)) {
        if (line.empty())
            continue;
        line.erase(line.find_last_not_of(" \t\n\r\f\v") + 1);
        string key, left, partOfSpeech, definition;
        size_t pos = line.find('|');
        if (pos != string::npos) {
            key = (line.substr(0, pos));
            left = line.substr(pos + 1);
        }

        stringstream ss(left);
        while (getline(ss, partOfSpeech, '|')) {
            pos = partOfSpeech.find(" -=>> ");
            if (pos != string::npos) {
                definition = partOfSpeech.substr(pos + 6);
                if (!definition.empty()) {
                    definition[0] = toupper(definition[0]);
                }
                partOfSpeech = partOfSpeech.substr(0, pos);

                dictionary[key].push_back({(key), partOfSpeech, definition});
            }
        }
    }
}

unordered_map<string, vector<Dictionary>> loadData(string filePath) {
    ifstream file;

    bool fileOpened = false;
    cout << "! Opening data file... " << filePath << endl;
    while (!fileOpened) {
        cout << "<!>ERROR<!> ===> File could not be opened." << endl;
        cout << "<!>ERROR<!> ===> Provided file path: " << filePath << endl;
        cout << "<!>Enter the CORRECT data file path: ";
        getline(cin, filePath);

        file.open(filePath);
        if (file.is_open()) {
            cout << "! Loading completed." << endl;
            cout << "! Closing data file... " << filePath << "" << endl;
            cout << "\n====== DICTIONARY 340 C++ =====" << endl;
            cout << "------ Keywords: 19" << endl;
            cout << "------ Definitions: 61\n" << endl;
            processFile(file);
            file.close();
            fileOpened = true;
        } else {
            cout << "Failed to open the file. Please try again." << endl;
        }
    }
    return dictionary;
}