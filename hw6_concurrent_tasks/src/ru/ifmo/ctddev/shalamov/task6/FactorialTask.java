package ru.ifmo.ctddev.shalamov.task6;


/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 02.04.14
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class FactorialTask implements Task<Integer, Integer> {

    /**
     * Empty constructor.
     * Does Nothing.
     */
    public FactorialTask() {
    }

    /**
     * Counts the factorial of bound.
     *
     * @param bound factorial's argument
     * @return factorial of bound: of 1* .... * bound
     */
    @Override
    public Integer run(Integer bound) {
        int ans = 1;
        for (int i = 2; i <= bound; ++i) {
            ans *= i;
        }
        return ans;
    }
}
