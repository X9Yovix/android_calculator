package mpdam.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView result, workingArea;
    private Button n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, opPlus, opMinus, opMultiply, opDivide, equal, clear;
    private String currentInput = "";
    private double firstValue = 0;
    private String operator = "";
    private boolean operatorClicked = false;
    private String oldValueInWorkingText = "";
    private boolean workingTextWithOp = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.result = findViewById(R.id.result);
        this.workingArea = findViewById(R.id.workingArea);

        this.n0 = findViewById(R.id.n0);
        this.n1 = findViewById(R.id.n1);
        this.n2 = findViewById(R.id.n2);
        this.n3 = findViewById(R.id.n3);
        this.n4 = findViewById(R.id.n4);
        this.n5 = findViewById(R.id.n5);
        this.n6 = findViewById(R.id.n6);
        this.n7 = findViewById(R.id.n7);
        this.n8 = findViewById(R.id.n8);
        this.n9 = findViewById(R.id.n9);

        this.equal = findViewById(R.id.equal);
        this.clear = findViewById(R.id.clear);

        this.opPlus = findViewById(R.id.opPlus);
        this.opDivide = findViewById(R.id.opDivide);
        this.opMinus = findViewById(R.id.opMinus);
        this.opMultiply = findViewById(R.id.opMultiply);

        this.n0.setOnClickListener(v -> onNumberButtonClick("0"));
        this.n1.setOnClickListener(v -> onNumberButtonClick("1"));
        this.n2.setOnClickListener(v -> onNumberButtonClick("2"));
        this.n3.setOnClickListener(v -> onNumberButtonClick("3"));
        this.n4.setOnClickListener(v -> onNumberButtonClick("4"));
        this.n5.setOnClickListener(v -> onNumberButtonClick("5"));
        this.n6.setOnClickListener(v -> onNumberButtonClick("6"));
        this.n7.setOnClickListener(v -> onNumberButtonClick("7"));
        this.n8.setOnClickListener(v -> onNumberButtonClick("8"));
        this.n9.setOnClickListener(v -> onNumberButtonClick("9"));

        this.opPlus.setOnClickListener(v -> onOperatorButtonClick("+"));
        this.opMinus.setOnClickListener(v -> onOperatorButtonClick("-"));
        this.opMultiply.setOnClickListener(v -> onOperatorButtonClick("*"));
        this.opDivide.setOnClickListener(v -> onOperatorButtonClick("/"));

        this.equal.setOnClickListener(v -> onEqualButtonClick());

        this.clear.setOnClickListener(v -> onClearButtonClick());
    }

    private void updateResultText() {
        System.out.println(this.currentInput);
        this.result.setText(this.currentInput);
    }

    private void onNumberButtonClick(String number) {
        if (this.operatorClicked) {
            this.currentInput = "";
            this.operatorClicked = false;
        }
        this.currentInput += number;
        System.out.println("before update area");
        System.out.println(this.currentInput);
        updateWorkingArea();
    }

    private void onOperatorButtonClick(String newOperator) {
        if (!currentInput.isEmpty() && operatorClicked) {
            operator = newOperator;
        } else if (!currentInput.isEmpty()) {
            firstValue = Double.parseDouble(currentInput);
            operator = newOperator;
            operatorClicked = true;
        } else if (operatorClicked) {
            operator = newOperator;
        }

        updateWorkingArea();
        currentInput = "";

    }

    private void updateWorkingArea() {
        String workingText = "";
        if (!currentInput.isEmpty() && operator.isEmpty() && !this.workingTextWithOp) {
            workingText = this.currentInput + " ";
            this.oldValueInWorkingText = workingText;
            System.out.println("workingText 1");
            System.out.println(workingText);
            this.workingArea.setText(workingText);
            return;
        }
        if (!operator.isEmpty() && !currentInput.isEmpty() && !this.workingTextWithOp) {
            workingText = this.oldValueInWorkingText + " " + this.operator;
            this.oldValueInWorkingText += " " + this.operator ;
            this.workingTextWithOp = true;
            System.out.println("workingText 2");
            System.out.println(workingText);
            workingArea.setText(workingText);
            return;
        }
        if(this.workingTextWithOp){
            workingText = oldValueInWorkingText + " " + currentInput;
            System.out.println("workingText 3");
            System.out.println(workingText);
            workingTextWithOp = false;
            workingArea.setText(workingText);
        }

    }

    private void onEqualButtonClick() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondValue = Double.parseDouble(currentInput);
            double resultValue = performCalculation(firstValue, secondValue, operator);
            currentInput = String.valueOf(resultValue);
            updateResultText();
            operator = "";
            workingTextWithOp = false;
            //workingArea.setText("");
        }
    }

    private void onClearButtonClick() {
        currentInput = "";
        firstValue = 0;
        operator = "";


        operatorClicked = false;
        updateResultText();
        workingArea.setText("");
        workingTextWithOp = false;
        workingArea.setText("");
    }

    private double performCalculation(double firstValue, double secondValue, String operator) {
        switch (operator) {
            case "+":
                return firstValue + secondValue;
            case "-":
                return firstValue - secondValue;
            case "*":
                return firstValue * secondValue;
            case "/":
                if (secondValue != 0) {
                    return firstValue / secondValue;
                } else {
                    System.out.println("div 0");
                    return Double.NaN;
                }
            default:
                return Double.NaN;
        }
    }

}