package ru.ifmo.ctddev.shalamov.task7;

import java.math.BigInteger;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class FactorialTask implements Task<Integer, BigInteger> {

    private Integer bound;


    @Override
    public String makeReport(BigInteger r) {
        String res = r.toString();
        return res;
    }

    /**
     * Constructs a FactorialTask and specifies the bound of factorial.
     *
     * @param bound always factorial of bound will be counted.
     */
    public FactorialTask(Integer bound) {
        this.bound = bound;
    }

    /**
     * Counts the factorial
     *
     * @return factorial
     */
    @Override
    public BigInteger doTask() {
        BigInteger ans = BigInteger.ONE;
        for (int i = 2; i <= bound; ++i) {
            ans = ans.multiply(BigInteger.valueOf(i));
        }
        return ans;
    }
}
