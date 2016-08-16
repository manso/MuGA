//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manso  &  Luis Correia                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package com.evolutionary.population;

import com.evolutionary.problem.Solution;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 5/out/2015, 18:52:43
 *
 * @author zulu - computer
 */
public class MultiPopulation extends SimplePopulation {

    /**
     * add one individual to the population if the individual exists in the
     * population the number of copies is increased
     *
     * @param ind
     */
    @Override
    public void addIndividual(Solution ind) {
        int index = indexOf(ind);
        if (index < 0) {
            //save number of copies
            int copies = ind.getNumberOfCopies();
            //add one copies
            super.addIndividual(ind); // simple population add
            //update number of copies
            ind.setNumberOfCopies(copies);
        } else {
            //update number of copies
            pop.get(index).addCopies(ind.getNumberOfCopies());
        }
    }

    /**
     * remove one individual to the population if the individual exists in the
     * population the number of copies is decreased
     *
     * @param ind
     */
    @Override
    public void removeIndividual(Solution ind) {
        int index = indexOf(ind);
        if (index >= 0 && pop.get(index).getNumberOfCopies() > 1) {
            pop.get(index).addCopies(-1); // decrease the number of copies
        } else {
            super.removeIndividual(ind); // remove genome
        }
    }

    /**
     * create random individuals
     *
     * @param template instance of proble
     */
    @Override
    public void createRandom(Solution template) {
        pop.clear();
        int size = maximumSize;
        this.random = template.random;
        Solution tmp;
        int tries = 3 * maximumSize; // number of tries to create unique individuals
        for (int i = 0; i < size && tries > 0; i++) {
            tmp = template.getClone(); // clone template
            tmp.randomize(); // randomize genome
            if (indexOf(tmp) < 0) {//unique individual ?
                pop.add(tmp); // add individual
            } else { //not unique
                size++; // do it again
                tries--; // decrease number of tries
            }
        }
    }

    /**
     * number of individuals in the population
     *
     * @return
     */
    @Override
    public int getNumberOfIndividuals() {
        int sum = 0;
        for (Solution ind : pop) {
            sum += ind.getNumberOfCopies();
        }
        return sum;
    }

    /**
     * expand multi individuals to array of single individuals
     *
     * @return
     */
    @Override
    public Solution[] getIndividualsReferences() {
        Solution[] array = new Solution[getNumberOfIndividuals()];
        int index = 0;
        for (Solution ind : pop) {
            for (int i = 0; i < ind.getNumberOfCopies(); i++) {
                Solution clone = ind.getClone();
                clone.setNumberOfCopies(1);
                clone.setGenome(ind.getGenome()); // share genome between copies
                array[index++] = clone;
            }
        }
        return array;
    }
    
     /**
     * expand multi individuals to array of single individuals
     *
     * @return
     */
    @Override
    public Solution[] getIndividuals() {
        Solution[] array = new Solution[getNumberOfIndividuals()];
        int index = 0;
        for (Solution ind : pop) {
            for (int i = 0; i < ind.getNumberOfCopies(); i++) {
                Solution clone = ind.getClone();
                clone.setNumberOfCopies(1);
                clone.setGenome(ind.getGenome()); // share genome between copies
                array[index++] = clone;
            }
        }
        return array;
    }

    /**
     * list of individuals (clones)
     *
     * @return List of individuals
     */
    public List<Solution> getIndividualtClonesList() {
        //list of clones
        List<Solution> list = new ArrayList<>();
        for (Solution ind : pop) {
            for (int i = 0; i < ind.getNumberOfCopies(); i++) {
                Solution clone = ind.getClone();
                clone.setNumberOfCopies(1);
                list.add(clone);
            }
        }
        return list;
    }

    private Solution getIndividualInPlainIndex(int plainIndex) {
        int plain = 0;
        for (Solution ind : pop) {
            plain += ind.getNumberOfCopies();
            if (plainIndex < plain) {
                if (ind.getNumberOfCopies() > 1) {
                    ind = ind.getClone();
                    ind.setNumberOfCopies(1);
                }
                return ind;
            }
        }
        throw new RuntimeException("getIndividualInPlainIndex - Index not defined " + plain);
    }

    @Override
    public Solution getRandom() {
        return getIndividualInPlainIndex(
                random.nextInt(getNumberOfIndividuals())
        ).getClone();
    }

    /**
     * get random Multi indiviual
     *
     * @return
     */
    public Solution getRandomMI() {
        return super.getRandom();
    }

    @Override
    public Solution removeRandom() {
        Solution rnd = getRandom();
        removeIndividual(rnd);
        return rnd;
    }

    public Solution removeRandomMI() {
        return pop.remove(random.nextInt(pop.size()));
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510051852L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
