package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.User;

public interface ChildRepo extends CrudRepository<Child, Long>{
	@Query("select c from Child c where c.user.username = :username")
	Child findByUsername(@Param("username") String username);

	@Query("select c from Child c where c.parent = :parent_id")
	List<Child> findByParent(@Param("parent_id") Parent parent);

	@Query("select c from Child c where c.user = :user")
	Child findByUser(@Param("user") User user);
}
