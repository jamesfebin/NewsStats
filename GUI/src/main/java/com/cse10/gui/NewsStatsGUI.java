package com.cse10.gui;

import com.cse10.article.*;
import com.cse10.database.DatabaseHandler;
import com.cse10.gui.task.classify.CeylonTodayClassifyTask;
import com.cse10.gui.task.classify.DailyMirrorClassifyTask;
import com.cse10.gui.task.classify.NewsFirstClassifyTask;
import com.cse10.gui.task.classify.TheIslandClassifyTask;
import com.cse10.gui.task.crawl.CeylonTodayCrawlTask;
import com.cse10.gui.task.crawl.DailyMirrorCrawlTask;
import com.cse10.gui.task.crawl.NewsFirstCrawlTask;
import com.cse10.gui.task.crawl.TheIslandCrawlTask;
import com.toedter.calendar.JDateChooser;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by TharinduWijewardane on 2015-01-08.
 */
public class NewsStatsGUI {

    private JFrame frame;

    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JPanel panelWizard;
    private JScrollPane scrollPaneCrawler;
    private JPanel panelCrawler;
    private JPanel panelCrawlPapers;
    private JPanel panelCrawlTimePeriod;
    private JPanel panelCrawlControl;
    private JPanel panelCrawlProgress;
    private JCheckBox ceylonTodayCrawlerCheckBox;
    private JCheckBox dailyMirrorCrawlerCheckBox;
    private JCheckBox newsFirstCrawlerCheckBox;
    private JCheckBox theIslandCrawlerCheckBox;
    private JDateChooser startCrawlDateChooser;
    private JDateChooser endCrawlDateChooser;
    private JButton startCrawlingButton;
    private JProgressBar ceylonTodayCrawlProgressBar;
    private JProgressBar dailyMirrorCrawlProgressBar;
    private JProgressBar newsFirstCrawlProgressBar;
    private JProgressBar theIslandCrawlProgressBar;
    private JProgressBar overallCrawlProgressBar;
    private JPanel panelStatusBar;
    private JLabel statusLabel;
    private JScrollPane scrollPaneClassifier;
    private JPanel panelClassifier;
    private JButton stopCrawlingButton;
    private JPanel panelClassifierProgress;
    private JPanel panelClassifierControl;
    private JPanel panelClassifierPapers;
    private JPanel panelClassifierTimePeriod;
    private JPanel panelCrawlResults;
    private ChartPanel chartPanelCrawler;
    private JPanel panelClassifierMOdel;
    private JCheckBox ceylonTodayClassifierCheckBox;
    private JCheckBox dailyMirrorClassifierCheckBox;
    private JCheckBox newsFirstClassifierCheckBox;
    private JCheckBox theIslandClassifierCheckBox;
    private JDateChooser startClassifyDateChooser;
    private JDateChooser endClassifyDateChooser;
    private JButton button1;
    private JButton startClassifyingButton;
    private JButton stopClassifyingButton;
    private JPanel panelClassifierResults;
    private JProgressBar ceylonTodayClassifyProgressBar;
    private JProgressBar dailyMirrorClassifyProgressBar;
    private JProgressBar newsFirstClassifyProgressBar;
    private JProgressBar theIslandClassifyProgressBar;
    private JProgressBar overallClassifyProgressBar;
    private ChartPanel chartPanelClassifier;

    private UIComponents uiComponentsAll;
    private UIComponents uiComponentsActive;

    private int ceylonTodayCrawlProgress;
    private int dailyMirrorCrawlProgress;
    private int newsFirstCrawlProgress;
    private int theIslandCrawlProgress;

    private int ceylonTodayClassifyProgress;
    private int dailyMirrorClassifyProgress;
    private int newsFirstClassifyProgress;
    private int theIslandClassifyProgress;

    private CeylonTodayCrawlTask ceylonTodayCrawlTask;
    private DailyMirrorCrawlTask dailyMirrorCrawlTask;
    private NewsFirstCrawlTask newsFirstCrawlTask;
    private TheIslandCrawlTask theIslandCrawlTask;

    private CeylonTodayClassifyTask ceylonTodayClassifyTask;
    private DailyMirrorClassifyTask dailyMirrorClassifyTask;
    private NewsFirstClassifyTask newsFirstClassifyTask;
    private TheIslandClassifyTask theIslandClassifyTask;

    private int numberOfCeylonTodayArticles;
    private int numberOfDailyMirrorArticles;
    private int numberOfNewsFirstArticles;
    private int numberOfTheIslandArticles;

    public NewsStatsGUI() {

        initComponentLists(); // initialize list containing UI components

        startCrawlingButton.addActionListener(new ActionListener() { //when crawler button is clicked
            @Override
            public void actionPerformed(ActionEvent e) {

                statusLabel.setText("Crawling...");

                disableCrawlerUI();
                uiComponentsActive.setNewCrawlerUI();

                if (ceylonTodayCrawlerCheckBox.isSelected()) {
                    ceylonTodayCrawlTask = new CeylonTodayCrawlTask(startCrawlDateChooser.getDate(), endCrawlDateChooser.getDate());
                    ceylonTodayCrawlTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                ceylonTodayCrawlProgress = progress;
                                ceylonTodayCrawlProgressBar.setValue(progress);
                                ceylonTodayCrawlProgressBar.setStringPainted(true);
                                setOverallCrawlProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CRAWLER, ceylonTodayCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CRAWLER, ceylonTodayCrawlProgressBar);
                    ceylonTodayCrawlTask.execute();
                }

                if (dailyMirrorCrawlerCheckBox.isSelected()) {
                    dailyMirrorCrawlTask = new DailyMirrorCrawlTask(startCrawlDateChooser.getDate(), endCrawlDateChooser.getDate());
                    dailyMirrorCrawlTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                dailyMirrorCrawlProgress = progress;
                                dailyMirrorCrawlProgressBar.setValue(progress);
                                dailyMirrorCrawlProgressBar.setStringPainted(true);
                                setOverallCrawlProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CRAWLER, dailyMirrorCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CRAWLER, dailyMirrorCrawlProgressBar);
                    dailyMirrorCrawlTask.execute();
                }

                if (newsFirstCrawlerCheckBox.isSelected()) {
                    newsFirstCrawlTask = new NewsFirstCrawlTask(startCrawlDateChooser.getDate(), endCrawlDateChooser.getDate());
                    newsFirstCrawlTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                newsFirstCrawlProgress = progress;
                                newsFirstCrawlProgressBar.setValue(progress);
                                newsFirstCrawlProgressBar.setStringPainted(true);
                                setOverallCrawlProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CRAWLER, newsFirstCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CRAWLER, newsFirstCrawlProgressBar);
                    newsFirstCrawlTask.execute();
                }

                if (theIslandCrawlerCheckBox.isSelected()) {
                    theIslandCrawlTask = new TheIslandCrawlTask(startCrawlDateChooser.getDate(), endCrawlDateChooser.getDate());
                    theIslandCrawlTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                theIslandCrawlProgress = progress;
                                theIslandCrawlProgressBar.setValue(progress);
                                theIslandCrawlProgressBar.setStringPainted(true);
                                setOverallCrawlProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CRAWLER, theIslandCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CRAWLER, theIslandCrawlProgressBar);
                    theIslandCrawlTask.execute();
                }

            }
        });
        stopCrawlingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (ceylonTodayCrawlTask != null) {
                    ceylonTodayCrawlTask.stopCrawling();
                    ceylonTodayCrawlTask.cancel(true);
                }
                if (dailyMirrorCrawlTask != null) {
                    dailyMirrorCrawlTask.stopCrawling();
                    dailyMirrorCrawlTask.cancel(true);
                }
                if (newsFirstCrawlTask != null) {
                    newsFirstCrawlTask.stopCrawling();
                    newsFirstCrawlTask.cancel(true);
                }
                if (theIslandCrawlTask != null) {
                    theIslandCrawlTask.stopCrawling();
                    theIslandCrawlTask.cancel(true);
                }

                statusLabel.setText("Ready");
                enableCrawlerUI();
                resetCrawlProgressBars();
            }
        });
        startClassifyingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Classifying...");

                disableClassifierUI();
                uiComponentsActive.setNewClassifierUI();

                if (ceylonTodayClassifierCheckBox.isSelected()) {
                    ceylonTodayClassifyTask = new CeylonTodayClassifyTask(startClassifyDateChooser.getDate(), endClassifyDateChooser.getDate());
                    ceylonTodayClassifyTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                ceylonTodayClassifyProgress = progress;
                                ceylonTodayClassifyProgressBar.setValue(progress);
                                ceylonTodayClassifyProgressBar.setStringPainted(true);
                                setOverallClassifyProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CLASSIFIER, ceylonTodayCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CLASSIFIER, ceylonTodayCrawlProgressBar);
                    ceylonTodayClassifyTask.execute();
                }

                if (dailyMirrorClassifierCheckBox.isSelected()) {
                    dailyMirrorClassifyTask = new DailyMirrorClassifyTask(startClassifyDateChooser.getDate(), endClassifyDateChooser.getDate());
                    dailyMirrorClassifyTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                dailyMirrorClassifyProgress = progress;
                                dailyMirrorClassifyProgressBar.setValue(progress);
                                dailyMirrorClassifyProgressBar.setStringPainted(true);
                                setOverallClassifyProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CLASSIFIER, dailyMirrorCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CLASSIFIER, dailyMirrorCrawlProgressBar);
                    dailyMirrorClassifyTask.execute();
                }

                if (newsFirstClassifierCheckBox.isSelected()) {
                    newsFirstClassifyTask = new NewsFirstClassifyTask(startClassifyDateChooser.getDate(), endClassifyDateChooser.getDate());
                    newsFirstClassifyTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                newsFirstClassifyProgress = progress;
                                newsFirstClassifyProgressBar.setValue(progress);
                                newsFirstClassifyProgressBar.setStringPainted(true);
                                setOverallClassifyProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CLASSIFIER, newsFirstCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CLASSIFIER, newsFirstCrawlProgressBar);
                    newsFirstClassifyTask.execute();
                }

                if (theIslandClassifierCheckBox.isSelected()) {
                    theIslandClassifyTask = new TheIslandClassifyTask(startClassifyDateChooser.getDate(), endClassifyDateChooser.getDate());
                    theIslandClassifyTask.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress" == evt.getPropertyName()) {
                                int progress = (Integer) evt.getNewValue();
                                theIslandClassifyProgress = progress;
                                theIslandClassifyProgressBar.setValue(progress);
                                theIslandClassifyProgressBar.setStringPainted(true);
                                setOverallClassifyProgress();
                            }
                        }
                    });
                    uiComponentsActive.addCheckBoxes(UIComponents.CLASSIFIER, theIslandCrawlerCheckBox);
                    uiComponentsActive.addProgressBars(UIComponents.CLASSIFIER, theIslandCrawlProgressBar);
                    theIslandClassifyTask.execute();
                }
            }
        });
        stopClassifyingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (ceylonTodayClassifyTask != null) {
                    ceylonTodayClassifyTask.stopClassification();
                    ceylonTodayClassifyTask.cancel(true);
                }
                if (dailyMirrorClassifyTask != null) {
                    dailyMirrorClassifyTask.stopClassification();
                    dailyMirrorClassifyTask.cancel(true);
                }
                if (newsFirstClassifyTask != null) {
                    newsFirstClassifyTask.stopClassification();
                    newsFirstClassifyTask.cancel(true);
                }
                if (theIslandClassifyTask != null) {
                    theIslandClassifyTask.stopClassification();
                    theIslandClassifyTask.cancel(true);
                }

                statusLabel.setText("Ready");
                enableClassifierUI();
                resetClassifyProgressBars();
            }
        });
    }

    public void init() {

        try {
//            UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
            UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("NewsStats");
        frame.setContentPane(new NewsStatsGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // packs the window according to components inside. this is not removed because its required to correct layouts

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0, 0, screenSize.width * 2 / 3, screenSize.height * 2 / 3);

        frame.setVisible(true);

    }

    private void createUIComponents() {
        // place custom component creation code here

        /* crawler chart */
        DefaultPieDataset pieDatasetCrawler = new DefaultPieDataset();
        pieDatasetCrawler.setValue("Ceylon Today", new Integer(10));
        pieDatasetCrawler.setValue("Daily Mirror", new Integer(20));
        pieDatasetCrawler.setValue("News First", new Integer(30));
        pieDatasetCrawler.setValue("The Island", new Integer(10));
        JFreeChart chartCrawler = ChartFactory.createPieChart3D("Crawled Articles", pieDatasetCrawler, true, true, true);
        chartPanelCrawler = new ChartPanel(chartCrawler);
        chartPanelCrawler.setVisible(false);

        /* classifier chart */
        final JFreeChart chart = ChartFactory.createBarChart(
                "Classified Articles",         // chart title
                "Type",               // domain axis label
                "Frequency",                  // range axis label
                null,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );
        chartPanelClassifier = new ChartPanel(chart);
        chartPanelClassifier.setVisible(true);
    }

    /**
     * lists of UI components. for extendability
     */
    private void initComponentLists() {

        uiComponentsAll = new UIComponents();
        uiComponentsActive = new UIComponents();

        //uiComponentsAll.addCheckBoxes(UIComponents.CRAWLER, ceylonTodayCrawlerCheckBox, dailyMirrorCrawlerCheckBox, newsFirstCrawlerCheckBox, theIslandCrawlerCheckBox);
        //uiComponentsAll.addCheckBoxes(UIComponents.CLASSIFIER, ceylonTodayClassifierCheckBox, dailyMirrorClassifierCheckBox, newsFirstClassifierCheckBox, theIslandClassifierCheckBox);

    }

    /* CRAWLER TAB */

    private void setOverallCrawlProgress() {

        int numOfSelectedPapers = uiComponentsActive.getCheckBoxes(UIComponents.CRAWLER).size();

        int overallProgress = (ceylonTodayCrawlProgress + dailyMirrorCrawlProgress + newsFirstCrawlProgress + theIslandCrawlProgress) / numOfSelectedPapers;

        overallCrawlProgressBar.setValue(overallProgress);
        overallCrawlProgressBar.setStringPainted(true);

        if (overallProgress == 100) {
            statusLabel.setText("Ready");
            InfoDialog infoDialog = new InfoDialog();
            infoDialog.init(frame, "Crawling Completed Successfully!");

            enableCrawlerUI();
            resetCrawlProgressBars();
            drawCrawlerChart();

        }
    }

    private void drawCrawlerChart() {

        numberOfCeylonTodayArticles = DatabaseHandler.getRowCount(CeylonTodayArticle.class);
        numberOfDailyMirrorArticles = DatabaseHandler.getRowCount(DailyMirrorArticle.class);
        numberOfNewsFirstArticles = DatabaseHandler.getRowCount(NewsFirstArticle.class);
        numberOfTheIslandArticles = DatabaseHandler.getRowCount(TheIslandArticle.class);

        DefaultPieDataset pieDatasetCrawler = new DefaultPieDataset();
        pieDatasetCrawler.setValue("Ceylon Today", numberOfCeylonTodayArticles);
        pieDatasetCrawler.setValue("Daily Mirror", numberOfDailyMirrorArticles);
        pieDatasetCrawler.setValue("News First", numberOfNewsFirstArticles);
        pieDatasetCrawler.setValue("The Island", numberOfTheIslandArticles);
        JFreeChart chartCrawler = ChartFactory.createPieChart3D("Crawled Articles", pieDatasetCrawler, true, true, true);
        chartPanelCrawler = new ChartPanel(chartCrawler);
        chartPanelCrawler.setVisible(true);
    }

    private void disableCrawlerUI() {

        ceylonTodayCrawlerCheckBox.setEnabled(false);
        dailyMirrorCrawlerCheckBox.setEnabled(false);
        newsFirstCrawlerCheckBox.setEnabled(false);
        theIslandCrawlerCheckBox.setEnabled(false);
        startCrawlDateChooser.setEnabled(false);
        endCrawlDateChooser.setEnabled(false);
        startCrawlingButton.setEnabled(false);

        stopCrawlingButton.setEnabled(true);
    }

    private void enableCrawlerUI() {

        ceylonTodayCrawlerCheckBox.setEnabled(true);
        dailyMirrorCrawlerCheckBox.setEnabled(true);
        newsFirstCrawlerCheckBox.setEnabled(true);
        theIslandCrawlerCheckBox.setEnabled(true);
        startCrawlDateChooser.setEnabled(true);
        endCrawlDateChooser.setEnabled(true);
        startCrawlingButton.setEnabled(true);

        stopCrawlingButton.setEnabled(false);
    }

    private void resetCrawlProgressBars() {

        ceylonTodayCrawlProgressBar.setValue(0);
        dailyMirrorCrawlProgressBar.setValue(0);
        newsFirstCrawlProgressBar.setValue(0);
        theIslandCrawlProgressBar.setValue(0);
        overallCrawlProgressBar.setValue(0);

        DatabaseHandler.closeDatabase(); //to close hibernate and let jvm stop
    }

    /* CLASSIFIER TAB */

    private void setOverallClassifyProgress() {

        int numOfSelectedPapers = uiComponentsActive.getCheckBoxes(UIComponents.CLASSIFIER).size();

        int overallProgress = (ceylonTodayClassifyProgress + dailyMirrorClassifyProgress + newsFirstClassifyProgress + theIslandClassifyProgress) / numOfSelectedPapers;

        overallClassifyProgressBar.setValue(overallProgress);
        overallClassifyProgressBar.setStringPainted(true);

        if (overallProgress == 100) {
            statusLabel.setText("Ready");
            InfoDialog infoDialog = new InfoDialog();
            infoDialog.init(frame, "Classifying Completed Successfully!");

            enableClassifierUI();
            resetClassifyProgressBars();
            drawClassifierChart();

        }
    }

    private void drawClassifierChart() {

        int ctArticlesCrime = DatabaseHandler.getRowCount(CeylonTodayArticle.class, "label", "crime");
        int ctArticlesNonCrime = DatabaseHandler.getRowCount(CeylonTodayArticle.class, "label", "other");

        int dmArticlesCrime = DatabaseHandler.getRowCount(DailyMirrorArticle.class, "label", "crime");
        int dmArticlesNonCrime = DatabaseHandler.getRowCount(DailyMirrorArticle.class, "label", "other");

        int nfArticlesCrime = DatabaseHandler.getRowCount(NewsFirstArticle.class, "label", "crime");
        int nfArticlesNonCrime = DatabaseHandler.getRowCount(NewsFirstArticle.class, "label", "other");

        int tiArticlesCrime = DatabaseHandler.getRowCount(TheIslandArticle.class, "label", "crime");
        int tiArticlesNonCrime = DatabaseHandler.getRowCount(TheIslandArticle.class, "label", "other");

        int crime = ctArticlesCrime + dmArticlesCrime + nfArticlesCrime + tiArticlesCrime;
        int nonCrime = ctArticlesNonCrime + dmArticlesNonCrime + nfArticlesNonCrime + tiArticlesNonCrime;

        // row keys...
        final String series1 = "Crime";
        final String series2 = "Non Crime";

        // column keys...
        final String category1 = "";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(crime, series1, category1);
        dataset.addValue(nonCrime, series2, category1);

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                "Classified Articles",         // chart title
                "Type",               // domain axis label
                "Frequency",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );
        chartPanelClassifier.setChart(chart);
        chartPanelClassifier.setVisible(true);
    }


    private void disableClassifierUI() {

        ceylonTodayClassifierCheckBox.setEnabled(false);
        dailyMirrorClassifierCheckBox.setEnabled(false);
        newsFirstClassifierCheckBox.setEnabled(false);
        theIslandClassifierCheckBox.setEnabled(false);
        startClassifyDateChooser.setEnabled(false);
        endClassifyDateChooser.setEnabled(false);
        startClassifyingButton.setEnabled(false);

        stopClassifyingButton.setEnabled(true);
    }

    private void enableClassifierUI() {

        ceylonTodayClassifierCheckBox.setEnabled(true);
        dailyMirrorClassifierCheckBox.setEnabled(true);
        newsFirstClassifierCheckBox.setEnabled(true);
        theIslandClassifierCheckBox.setEnabled(true);
        startClassifyDateChooser.setEnabled(true);
        endClassifyDateChooser.setEnabled(true);
        startClassifyingButton.setEnabled(true);

        stopClassifyingButton.setEnabled(false);
    }

    private void resetClassifyProgressBars() {

        ceylonTodayClassifyProgressBar.setValue(0);
        dailyMirrorClassifyProgressBar.setValue(0);
        newsFirstClassifyProgressBar.setValue(0);
        theIslandClassifyProgressBar.setValue(0);
        overallClassifyProgressBar.setValue(0);

        DatabaseHandler.closeDatabase(); //to close hibernate and let jvm stop
    }
}
