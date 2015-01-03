package com.cse10.database;

/**
 * Created by TharinduWijewardane on 02.07.2014.
 */

import com.cse10.article.Article;
import com.cse10.entities.CrimeEntityGroup;
import com.cse10.entities.CrimePerson;
import com.cse10.entities.LocationDistrictMapper;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseHandler {

    /**
     * insert an article (table will be selected according to the type of object)
     *
     * @param article
     */
    public static void insertArticle(Article article) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(article);
        session.getTransaction().commit();
    }

    /**
     * insert multiple articles (table will be selected according to the type of object)
     *
     * @param articles
     */
    public static void insertArticles(List<? extends Article> articles) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        for (Article article : articles) {
            session.save(article);
        }
        session.getTransaction().commit();
    }

    /**
     * fetch articles of given class (given table)
     *
     * @param articleClass ex:- CeylonTodayArticle.class
     * @return
     */
    public static List<Article> fetchArticles(Class articleClass) {
        ArrayList<Article> articles;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        articles = (ArrayList<Article>) session.createCriteria(articleClass).list();
        session.getTransaction().commit();

        return articles;
    }

    /**
     * fetch articles of given class (given table) which have the specified IDs
     *
     * @param articleClass ex:- CeylonTodayArticle.class
     * @param idList list of ids which the fetched rows should have
     * @return
     */
    public static List<Article> fetchArticlesByIdList(Class articleClass, List<Integer> idList) {

        ArrayList<Article> articles;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        articles = (ArrayList<Article>) session.createCriteria(articleClass)
                .add(Restrictions.in("id", idList))
                .list();
        session.getTransaction().commit();

        return articles;
    }

    /**
     *
     * @param articleClass ex:- Article.class
     * @param startId start id (inclusive)
     * @param endId end id (inclusive)
     * @return
     */
    public static List<Article> fetchArticlesByIdRange(Class articleClass, int startId, int endId) {

        ArrayList<Article> articles;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        articles = (ArrayList<Article>) session.createCriteria(articleClass)
                .add(Restrictions.ge("id", startId))
                .add(Restrictions.le("id", endId))
                .list();
        session.getTransaction().commit();

        return articles;
    }

    /**
     * execute a query without using hibernate and return ResultSet
     *
     * @param query
     * @return
     */
    public static ResultSet executeQuery(String query) {

        java.sql.Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DatabaseConstants.DB_URL, DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            rs = st.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rs;
        }
    }

    /**
     * insert an object containing crime entities
     *
     * @param crimeEntityGroup
     */
    public static void insertCrimeEntities(CrimeEntityGroup crimeEntityGroup) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(crimeEntityGroup);
        session.getTransaction().commit();
    }

    /**
     * insert multiple objects containing crime entities
     *
     * @param crimeEntityGroups
     */
    public static void insertCrimeEntityGroups(List<CrimeEntityGroup> crimeEntityGroups) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        for (CrimeEntityGroup crimeEntityGroup : crimeEntityGroups) {
            session.save(crimeEntityGroup);
        }
        session.getTransaction().commit();
    }


    /**
     * fetch objects containing crime entities
     * @return
     */
    public static ArrayList<CrimeEntityGroup> fetchCrimeEntityGroups() {
        ArrayList<CrimeEntityGroup> entityGroups;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        entityGroups = (ArrayList<CrimeEntityGroup>) session.createCriteria(CrimeEntityGroup.class).list();
        session.getTransaction().commit();

        return entityGroups;
    }


    /**
     * insert details of a certain location
     */
    public static void insertLocationDistrict(LocationDistrictMapper locationDistrict) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(locationDistrict);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * fetch details of a certain location
     *
     * @param location name of the location
     * @return LocationDistrictMapper
     */
    public static LocationDistrictMapper fetchLocation(String location) {

        LocationDistrictMapper locationDistrict = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        locationDistrict = (LocationDistrictMapper)session.load(LocationDistrictMapper.class, location);
        Hibernate.initialize(locationDistrict);
        session.getTransaction().commit();
        session.close();

        return locationDistrict;
    }

    /**
     * insert details of a certain location
     */
    public static void insertCrimePerson(CrimePerson crimePerson) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(crimePerson);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * fetch people involved in a crime
     *
     * @param entityGroupID
     * @return ArrayList<CrimePerson>
     */
    public static ArrayList<CrimePerson> fetchCrimePeople(int entityGroupID) {

        ArrayList<CrimePerson> crimePersonList;
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        crimePersonList = (ArrayList<CrimePerson>)session.createCriteria(CrimePerson.class).list();
        Hibernate.initialize(crimePersonList);
        session.getTransaction().commit();
        session.close();

        return crimePersonList;
    }

    /**
     * insert details of a certain location
     */
    public static void insertCrimeDetails(CrimeEntityGroup crimeEntityGroup, HashSet<String> crimePeopleSet) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        session.save(crimeEntityGroup);

        if (crimePeopleSet != null && !crimePeopleSet.isEmpty()){

            //Set<CrimePerson> crimePersonSet = new HashSet<>();
            for(String person : crimePeopleSet){
                CrimePerson crimePerson = new CrimePerson();
                crimePerson.setName(person);
                crimePerson.setEntityGroup(crimeEntityGroup);
                session.save(crimePerson);
                crimeEntityGroup.getCrimePersonSet().add(crimePerson);
            }
            //crimeEntityGroup.setCrimePersonSet(crimePersonSet);

        }

        session.save(crimeEntityGroup);
        session.getTransaction().commit();
        session.close();
    }
}
