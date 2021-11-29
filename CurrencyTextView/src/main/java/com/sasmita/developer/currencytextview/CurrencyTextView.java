package com.sasmita.developer.currencytextview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class CurrencyTextView extends TextView {

    private String amount, format, currencyType, customSymbol;
    private TypedArray typedArray;
    private String currencyCodeGravity;
    private static final String TAG = "CurrencyTextView";


    public CurrencyTextView(@NonNull Context context) {
        super(context);
    }

    public CurrencyTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CurrencyTextView, 0, 0);
        initialize();
        function();
    }

    public CurrencyTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initialize() {
        try {
            amount = typedArray.getString(R.styleable.CurrencyTextView_amount);
            customSymbol = typedArray.getString(R.styleable.CurrencyTextView_custom_symbol);
            currencyType = typedArray.getString(R.styleable.CurrencyTextView_currency_type);
            currencyCodeGravity = typedArray.getString(R.styleable.CurrencyTextView_currency_code_gravity);
        } finally {
            typedArray.recycle();
        }
    }

    void function() {
        if (getCurrencyType() != null) {
            switch (getCurrencyType()) {
                case "IDR":
                    currencyIDR(getAmount());
                    break;

                case "USD":
                    currencyUSD(getAmount());
                    break;

                case "custom":
                    currencyCustom(getAmount());
                    break;

                default:
                    currencyDefault(getCurrencyType());
            }
        } else {
            setCurrencyType("IDR");
        }
    }



    /**
     * Return formatted number in IDR with currency symbol
     * @param text amount in a String base on double, float or integer format
     */
    void currencyIDR(String text) {
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            dfs.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##", dfs);
            format = decimalFormat.format(Double.parseDouble(text));
        } catch (Exception e) {
            Log.d(TAG, "currencyIDR: " + e);
        }

        currencyGravity("Rp", getCurrencyType(), format);
    }

    /**
     * Return formatted number in USD with currency symbol
     * @param text amount in a String base on double, float or integer format
     */
    void currencyUSD(String text) {
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            dfs.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##", dfs);
            format = decimalFormat.format(Double.parseDouble(text));
        } catch (Exception e) {
            Log.d(TAG, "currencyUSD: " + e);
        }

        currencyGravity("$", getCurrencyType(), format);
    }

    /**
     * Return only formatted number, can also make your own currency symbol.
     * Please set customSymbol in "none" if you don't want to use any currency code
     * @param text amount in a String base on double, float or integer format
     */
    void currencyCustom(String text) {
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            dfs.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##", dfs);
            format = decimalFormat.format(Double.parseDouble(text));
        } catch (Exception e) {
            Log.d(TAG, "currencyCustom: " + e);
        }

        if (getCustomSymbol() == null) {
            setText("{set your customSymbol}" + format);
        } else if (getCustomSymbol().equals("none")) {
            setText(format);
        } else {
            currencyGravity(getCustomSymbol(), getCustomSymbol(), format);
        }
    }

    private void currencyGravity(String symbol_left, String symbol_right, String format) {
        if (getCurrencyCodeGravity() != null) {
            if (getCurrencyCodeGravity().equals("right"))
                setText(format + " " + symbol_right);
            else
                setText(symbol_left + format);
        } else {
            setText(symbol_left + format);
        }
    }

    /**
     * Using NumberFormat for format text
     * @param symbol currency code in capslock
     */
    public void currencyDefault(String symbol) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(Currency.getInstance(symbol));
        setText(numberFormat.format(Double.parseDouble(getAmount())));
    }


    // Getter and Setter

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        function();
    }

    public String getCustomSymbol() {
        return customSymbol;
    }

    public void setCustomSymbol(String customSymbol) {
        this.customSymbol = customSymbol;
        function();
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
        function();
    }

    public String getCurrencyCodeGravity() {
        return currencyCodeGravity;
    }

    public void setCurrencyCodeGravity(String currencyCodeGravity) {
        this.currencyCodeGravity = currencyCodeGravity;
        function();
    }
}
