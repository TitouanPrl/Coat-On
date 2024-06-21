package io.coaton.activity.repository;

import io.coaton.activity.model.Activity;
import io.coaton.activity.model.ActivityTags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, BigInteger> {
    // NOTE: Add any custom CRUD operations here
    //  - Structure of operations are based on the method name
    //  - Read more here: https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.query-methods.query-creation

    @Query("SELECT a FROM Activity a WHERE " +
            "a.tags.localeType = ?1 " +
            "OR a.tags.exertionLevel = ?2 " +
            "OR a.tags.category = ?3 " +
            "OR a.tags.temperature = ?4 " +
            "OR a.tags.wind = ?5 " +
            "OR a.tags.minPeople = ?6 " +
            "OR a.tags.maxPeople = ?7 " +
            "OR a.tags.recommendedPeople = ?8 ")
    public List<Activity> findActivityByTags(ActivityTags.Locale locale,
                                             ActivityTags.Exertion exertionLevel,
                                             ActivityTags.Category category,
                                             ActivityTags.Temperature temperature,
                                             ActivityTags.Wind wind,
                                             Integer minPeople,
                                             Integer maxPeople,
                                             Integer recommendedPeople);
}
