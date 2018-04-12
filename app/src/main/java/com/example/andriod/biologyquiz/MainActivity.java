package com.example.andriod.biologyquiz;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {




//create 2 global variables found in Category section of app
// radioGroup and Radio button, both will be setup after user clickes select button on that linear layout
    private RadioGroup radioGroupCategories;
    private RadioButton radioButtonCategories;
    private Button submitButton, selectButton;
//    3 objects, initialQuestion sets up lineary layout of Questions on launch of app
//    endQuestion informs user that the category they selected has no more question
//    error_return lets user know an error occured on while loop(in questionSelection Function) app may still be usuable
    question initialQuestion, endQuestion, errorReturn;


    question animalQuestion1, animalQuestion2, animalQuestion3;
    question cellQuestion1, cellQuestion2, cellQuestion3;
    question geneticsQuestion1, geneticsQuestion2, geneticsQuestion3;
    //address holder of question object that is being currently displayed on screen, used to change boolean and see if user answered question correctly
    //selected Question is what is retrieved from findQuestion, needs to be global to have a delay in setUpSelectedQuestion functions to display after Toast
    question activeAppQuestion, selectedQuestion;

    List<question> animalQuestionsList = new ArrayList<question>();
    List<question> cellQuestionsList = new ArrayList<question>();
    List<question> geneticQuestionsList = new ArrayList<question>();


    int scorePlayer1, scorePlayer2 = 0;

    boolean playerTurn = false;
//delay of 1 sec, used after player turn display screen is shown. Allows the question to be shown right after, uses handler below
    int threadDelayMin = 1000;
//used to delay setUpQuestion Function, so that the questions are shown after Player Turn toast is displayed
    Handler handler= new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInitialEndQuestions();
        setUpAnimalQuestions();
        setUpCellQuestions();
        setUpGeneticQuestions();
        selectedQuestionSetUp(initialQuestion);
        displayScore();

        submitButton = (Button) findViewById(R.id.submitButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        // displayPlayerTurn(playerTurn);

        animalQuestionsList.add(animalQuestion1);
        animalQuestionsList.add(animalQuestion2);
        animalQuestionsList.add(animalQuestion3);
        cellQuestionsList.add(cellQuestion1);
        cellQuestionsList.add(cellQuestion2);
        cellQuestionsList.add(cellQuestion3);
        geneticQuestionsList.add(geneticsQuestion1);
        geneticQuestionsList.add(geneticsQuestion2);
        geneticQuestionsList.add(geneticsQuestion3);


    }

//Functions on initial setup of databases for Displaying questions Section

    /*
    sets up 3 objects that will be need on creation of app
    initialQuestion, endQuestion, error_return
    app uses all 3, initial question is set up in questions LinearLayout to imply user to select a category
    endQuestion informs user that the category they had selected is done with and they must select another question
    error_return informs user that an error has occured, app may still be usuable, but while loop error in questionSelection
    */
    private void setUpInitialEndQuestions() {

        int random = (int) (Math.random() * 5 + 1);


        Drawable higherPrimateImage = getResources().getDrawable(R.drawable.higherprimate_cosmo_shiva);


        if (random == 1) {
            higherPrimateImage = getResources().getDrawable(R.drawable.higherprimate);

        } else if (random == 2) {
            higherPrimateImage = getResources().getDrawable(R.drawable.higherprimate2);
        } else if (random == 3) {
            higherPrimateImage = getResources().getDrawable(R.drawable.higherprimate_stonedape);
        } else if (random == 4) {
            higherPrimateImage = getResources().getDrawable(R.drawable.higherprimate_babydna);
        }


        initialQuestion = new question(higherPrimateImage,
                "Will you be the HIGHEST PRIMATE!!!!!!",
                "ACHIEVE",
                "YOUR",
                "PRIMATE",
                -100,
                true);
        endQuestion = new question(getResources().getDrawable(R.drawable.higherprimate_question_done),
                "Category Section all questions have been answered, Please Select another Category",
                "Select",
                "Another",
                "Category",
                -100,
                true);

        errorReturn = new question(getResources().getDrawable(R.drawable.error_return),
                "ERROR LOOP CORRUPTED MUST FIX PROGRAMMING",
                "ERROR",
                "SENDQUESTIONSSETUP",
                "FUNCTION",
                -200,
                true);


    }


    //sets up animal questions to select from
    private void setUpAnimalQuestions() {

        animalQuestion1 = new question(getResources().getDrawable(R.drawable.grizzly_bear_cubs),
                "What Family Category is the Grizzly Bear Found in?",
                "Ursidae",
                "Cervidae",
                "Canidae",
                1,
                false);

        animalQuestion2 = new question(getResources().getDrawable(R.drawable.redwood),
                "What is the average lifespan for Redwood Trees?",
                "100-300 Years",
                "500-700 Years",
                "1,000 -1,500 Years",
                2,
                false);
        animalQuestion3 = new question(getResources().getDrawable(R.drawable.shark),
                "What is the Special Sensing Organ that Sharks have that use electroreceptors, that make a jelly-filled pores that sense weak Electric Fields in the water to help it hunt?",
                "Ampullae of Lorenzini",
                "Ampere gland",
                "Lateral Line System",
                1,
                false);
    }

    //sets up cell questions to select from
    private void setUpCellQuestions() {

        cellQuestion1 = new question(getResources().getDrawable(R.drawable.cell_mitosis),
                "This Cell is in which of the following stages?",
                "Anaphase",
                "G2",
                "Cytokinesis",
                2,
                false);

        cellQuestion2 = new question(getResources().getDrawable(R.drawable.cell_prometaphase),
                "This cell is in which stage of Mitosis?",
                "Early Prophase",
                "Late Prophase(prometaphase)",
                "Metaphase",
                2,
                false);
        cellQuestion3 = new question(getResources().getDrawable(R.drawable.cell_anaphase),
                "This Cell is in which stage of Mitosis?",
                "Anaphase",
                "Early Prophase",
                "Telophase",
                1,
                false);
    }

    //sets up genetic questions to select from
    private void setUpGeneticQuestions() {

        geneticsQuestion1 = new question(getResources().getDrawable(R.drawable.genetics_grayrabbit),
                "Suppose a white-furred rabbit breeds with a black-furred rabbit and all of their offspring have a phenotype of gray fur. what does the gene for fur color in rabbits appear to be an example of?",
                "Codominance",
                "Incomplete dominance",
                "Complete dominance",
                2,
                false);

        geneticsQuestion2 = new question(getResources().getDrawable(R.drawable.genetics_blood),
                "A person with type-B blood has children with a person of type- AB blood, what blood types could their children have?",
                "Type-AB, Type-A, and Type-B",
                "Type-AB, and Type-B",
                "Type- A, and Type- B",
                1,
                false);
        geneticsQuestion3 = new question(getResources().getDrawable(R.drawable.genetics_dna),
                "When DNA is written to RNA what Nucleobase is changed to Uracil(U)?",
                "Adenine",
                "Thymine",
                "Cytosine",
                2,
                false);
    }


//Functions on Category Selection and Changing Questions randomly based on Selection

    /*
    Activates game, when Select Button is clicked on Category page
    Deciphers what RadioButton has been clicked
    Will send radioButtonCategories and index to sendQuestion Category if item was selected
    if not will prompt user to select a category
    sends display player turn as well, after question is selected and able to be prompted
    */
    public void onSelect(View view) {

//      sets up radioGroupCategories to be identified with category section & than find what radiobutton was selected
//      sends it to the sendQuestion function if something was selected
        radioGroupCategories = (RadioGroup) findViewById(R.id.radioGroupCategories);
        int selectedID = radioGroupCategories.getCheckedRadioButtonId();

        radioButtonCategories = (RadioButton) findViewById(selectedID);

        if (radioGroupCategories.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select a Category", Toast.LENGTH_SHORT).show();
        } else {
            int idx = radioGroupCategories.indexOfChild(radioButtonCategories);

            sendQuestion(radioButtonCategories, idx);


            if (activeAppQuestion.getAnswerChoiceChar() != -100) {
                selectButton.setClickable(false);
                submitButton.setClickable(true);

            }

        }
    }

    /*
    Checks to see what category was chosen
    Than Selects a question that was not asked previously or was answered incorrectly
    Than sends question to sendQuestionSetup function to display new question on app
    Do not need if statement, as index is used in questionSelection function, if statment is made just for Toast capabilities
    uses Handler to delay showing selectedQuestionSetup, so that player turn toast is displayed after halting
    @ category not needed, but radiobutton catgory
    @ idx index that the user has chosen, used to obtain correct list of questions to decipher from
    */
    private void sendQuestion(RadioButton category, int idx) {

/* NOT NEEDED, MAY COMMENT OUT ONLY INPUTTED FOR TOAST CAPABILITY TO INFORM USER WHAT THEY CLICKED */
//        if (idx == 2) {
//            Toast.makeText(this, "Genetics Clicked", Toast.LENGTH_SHORT).show();
//        } else if (idx == 1) {
//            Toast.makeText(this, "Cells Clicked", Toast.LENGTH_SHORT).show();
//        } else if (idx == 0) {
//            Toast.makeText(this, "Animals Clicked", Toast.LENGTH_SHORT).show();
//        }
//GETS QUESTION TO SEND TO QUESTION SETUP
        selectedQuestion = questionSelection(idx);

        playerTurn = displayPlayerTurn(playerTurn, selectedQuestion);

        handler.postDelayed(new Runnable(){

            @Override
            public void run()
            {
                selectedQuestionSetUp(selectedQuestion);

            }

        }, threadDelayMin);

        activeAppQuestion = selectedQuestion;
    }


    /*
    Used to find a question to set up
    Finds the questions on random, and avoids questions that have been selected already
    loops through until it finds a question or if all questions in category have been found already sends endQuestion
    if there is an error in loops core programming error_return will be sent to user as a result
    @param questionIndex is the the index selected from categories section, used to decipher what question banks to look through
    */
//    private question questionSelection(int questionIndex) {
//
//        int random;
//
//        boolean questionFound = false;
//
//        while (questionFound == false) {
//
//            random = (int) (Math.random() * 3 + 1);
//
//
//            if (questionIndex == 0) {
//
//                if (random == 1 && animalQuestion1.isChosen() == false) {
//
//
//                    return animalQuestion1;
//
//                } else if (random == 2 && animalQuestion2.isChosen() == false) {
//
//
//                    return animalQuestion2;
//
//                } else if (random == 3 && animalQuestion3.isChosen() == false) {
//
//
//                    return animalQuestion3;
//
//                } else if (animalQuestion1.isChosen() && animalQuestion2.isChosen() && animalQuestion3.isChosen()) {
//
//                    questionFound = true;
//                    return endQuestion;
//
//                }
//            } else if (questionIndex == 1) {
//                return endQuestion;
//
//
//            } else if (questionIndex == 2) {
//
//                return endQuestion;
//
//            }
//
//
//        }
//        return errorReturn;
//    }

    /*
    * used to find what question to input in xml layout Quesitoin section
    * uses index to send correct list to questionFinder
    * if all questions in category are used (chosen and answered correctly) will send endQuesiton
    * if error occurs will send errorReturn
    * @questionIndex is the index the user selected in category section, needed to determine what list to look through for question
    * */
    private question questionSelection(int questionIndex) {
        boolean questionFound = false;


        //   animalQuestionsList.removeIf(animalQuestionsList->animalQuestionsList.isChosen());

        question questionReturn = endQuestion;

        if (questionIndex == 0) {
            removeQuestionFromList(animalQuestionsList);

            return questionFinder(animalQuestionsList);

        } else if (questionIndex == 1) {

            removeQuestionFromList(cellQuestionsList);

            return questionFinder(cellQuestionsList);

        } else if (questionIndex == 2) {

            removeQuestionFromList(geneticQuestionsList);

            return questionFinder(geneticQuestionsList);
        }


        return errorReturn;
    }

    //returns question to  questionSelection, based off anymore questions exist in list sent
    // if no questions exist returns endQuestion
    //@myList list that is sent to examine what question to send back
    private question questionFinder(List myList) {

        int maxRandom = myList.size();

        int random = (int) (Math.random() * maxRandom + 0);

        if (myList.size() == 0) {

            return endQuestion;
        } else {
            return (question) myList.get(random);

        }


    }


    //removes question from list that is sent if the question has chosen as true
//    @myList list that is sent to be looked at
    private void removeQuestionFromList(List myList) {


//        ListIterator<question> iter = myList.listIterator();
//        while(iter.hasNext()){
//            if(iter.next().isChosen()){
//                iter.remove();
//            }
//        }


        for (int i = 0; i < myList.size(); i++) {

            question removeQuestion = (question) myList.get(i);

            if (removeQuestion.isChosen()) {

                myList.remove(i);
                i--;
            }

        }
    }


    /*
    Function used to set up Questions Linear layout
    @param questionSelected is an object that is used to layout the ids correctly and swap them for new information
    */
    private void selectedQuestionSetUp(question questionSelected) {

        ImageView imageQuestion = (ImageView) findViewById(R.id.questionImage);
        TextView textQuestion = (TextView) findViewById(R.id.questionDescription);
        RadioButton answerA = (RadioButton) findViewById(R.id.answerChoiceA);
        RadioButton answerB = (RadioButton) findViewById(R.id.answerChoiceB);
        RadioButton answerC = (RadioButton) findViewById(R.id.answerChoiceC);

        textQuestion.setText(questionSelected.getQuestion());
        imageQuestion.setImageDrawable(questionSelected.getImage());
        answerA.setText(questionSelected.get_a_Answer());
        answerB.setText(questionSelected.get_b_Answer());
        answerC.setText(questionSelected.get_c_Answer());


    }


//Functions on checking to see if answer in Questions is correct

    /*
    * is used after user choses submit button, creates local variables for linear layout of Quesitons
    * RadioGroup,RadioButton to see what was chosen from user as index choice
    * sends outputs to various funcitons answerCorrect, displayScore
    * makes select button active again and disables submit
    *
    *Functions sent to:
    *
    * */
    public void onSubmit(View view) {

        RadioGroup questionsRadioGroup = (RadioGroup) findViewById(R.id.radioGroupQuestions);
        int selectButtonId = questionsRadioGroup.getCheckedRadioButtonId();
        RadioButton questionButtonSelected = (RadioButton) findViewById(selectButtonId);


        if (questionsRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select an Answer!", Toast.LENGTH_SHORT).show();
        } else {
            int questionAnswer = questionsRadioGroup.indexOfChild(questionButtonSelected) + 1;

            activeAppQuestion.setChosen(answerCorrect(activeAppQuestion, questionAnswer));


            displayScore();
            submitButton.setClickable(false);
            selectButton.setClickable(true);

        }


    }

    //check displayScore to see why player 1 and 2 get scored based on player turn
    /*
    * @activeAppQuestion is the current questions displaying on the app, is using the address to manipulate and access all items. Retrieved after selection occurs onselect
    *   check onSelect mapping to see creation and way its made
    *  @answerindex is the index of the button that is currently active, is used to check to see if answer is correct from players selection
    *
    *  checks to see if players choice was correct than scores player based on who is chosen, makes toast informing user if they were correct or not
    * */
    private boolean answerCorrect(question activeAppQuestion, int answerIndex) {

        if (answerIndex == activeAppQuestion.getAnswerChoiceChar()) {

            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();

            if (playerTurn) {
                scorePlayer1 = changeScore(scorePlayer1);
            } else {
                scorePlayer2 = changeScore(scorePlayer2);
            }

            return true;
        } else {
            Toast.makeText(this, "WRONG", Toast.LENGTH_SHORT).show();

            return false;
        }

    }


//Functions on handling player scores and displaying them correctly // Showing who's turn it is

    /*
    *
    * displays the score of each player
    * */
    void displayScore() {

        TextView playerOne = (TextView) findViewById(R.id.playerOne);
        TextView playerTwo = (TextView) findViewById(R.id.playerTwo);

        playerOne.setText("Player 1 Score: " + scorePlayer1);
        playerTwo.setText("Player 2 Score: " + scorePlayer2);


    }

    /*
    * displays who's turn it is in the game
    * @playerturn sends and retrieves a change in player turn. Player 1= True/ Player 2= False.
    * @selectedQuesiton checks to see if ischosen is set to true, doesn't change player. allows for another question to be asked. occurs when category is done with questions
    * changes who's turn after image is displayed in toast. toggles back and forth
    */
    private boolean displayPlayerTurn(boolean playerTurn, question selectedQuestion) {

        int chosen = selectedQuestion.getAnswerChoiceChar();


        if (chosen < 0) {
            return playerTurn;
        } else {

//starts off with false so player 1 become true after category gets launched, allows for score to work correctly as well on answerCorrect
//Uses global varialbe thread setup at length of Toast Long.
            if (playerTurn == false) {

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_player_1_turn_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.FILL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

                return true;


            } else {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_player_2_turn_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container_2));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.FILL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                return false;

            }
        }


    }

    //Changes the players score, uses global variables playerScore1|| playerScore2
    //
    private int changeScore(int activePlayerScore) {

        return activePlayerScore = activePlayerScore + 1;
    }



    // restarts main activity to allow new game to be played based off clicking restart app
    public void restartApp(View view) {

        restartSelf();

    }

    private void restartSelf() {
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 1000, // one second
                PendingIntent.getActivity(this, 0, getIntent(), PendingIntent.FLAG_ONE_SHOT
                        | PendingIntent.FLAG_CANCEL_CURRENT));
        finish();
    }


    }



//            radioSelectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //get selected radio button from radioGroup
//                int selectedId=radioGroupCategories.getCheckedRadioButtonId();
//
//                // find the radiobutton by return id
//                radioButtonCategories=(RadioButton) findViewById(selectedId);
//
//                Toast.makeText(this,
//                        radioButtonCategories.getText(), Toast.LENGTH_SHORT).show();
//
//
//            }