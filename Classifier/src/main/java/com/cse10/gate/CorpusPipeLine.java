package com.cse10.gate;

import gate.*;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * pipe line which is used for document processing
 * Created by Chamath on 12/20/2014.
 */
public class CorpusPipeLine {

    private SerialAnalyserController serialAnalyserController;

    /**
     * add required processing resources and configure the pipe line
     *
     * @param isPOSRequired check whether part of speech tagging is required
     */
    public void configure(boolean isPOSRequired) {
        try {
            //load the plugin ANNIE first to use resources under that
            try {
                Gate.getCreoleRegister().registerDirectories(new File(Gate.getPluginsHome(), "ANNIE").toURI().toURL());
            } catch (GateException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //create a application using the contorller
            serialAnalyserController = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController",
                    Factory.newFeatureMap(),
                    Factory.newFeatureMap(), "TestOne");
            // load each processing resource
            FeatureMap params = Factory.newFeatureMap();
            //create each processing resource and add to application
            ProcessingResource annotationDeletePR = (ProcessingResource) Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR", params);
            ProcessingResource defaultTokeniser = (ProcessingResource) Factory.createResource("gate.creole.tokeniser.DefaultTokeniser", params);

            //if POS is required, then use default gazetter lists.
            if (!isPOSRequired) {
                try {
                    params.put("listsURL", new File("Classifier\\src\\main\\resources\\gazetterLists\\lists.def").toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            ProcessingResource defaultGazetteer = (ProcessingResource) Factory.createResource("gate.creole.gazetteer.DefaultGazetteer", params);
            params.clear();
            ProcessingResource sentenceSplitter = (ProcessingResource) Factory.createResource("gate.creole.splitter.SentenceSplitter", params);
            ProcessingResource posTagger = (ProcessingResource) Factory.createResource("gate.creole.POSTagger", params);
            ProcessingResource ANNIETransducer = (ProcessingResource) Factory.createResource("gate.creole.ANNIETransducer", params);

            serialAnalyserController.add(annotationDeletePR);
            serialAnalyserController.add(defaultTokeniser);
            serialAnalyserController.add(defaultGazetteer);
            if (isPOSRequired) {
                serialAnalyserController.add(sentenceSplitter);
                serialAnalyserController.add(posTagger);
                serialAnalyserController.add(ANNIETransducer);
            }

        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(CorpusPipeLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * set corpus for processing
     *
     * @param corpus
     */
    public void setCorpus(Corpus corpus) {
        serialAnalyserController.setCorpus(corpus);
    }

    /**
     * execute pipeline
     *
     * @throws ExecutionException
     */
    public void execute() throws ExecutionException {
        serialAnalyserController.execute();
    }
}
