package com.theagilemonkeys.devTest.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.theagilemonkeys.devTest.beans.Customer;

@Mapper
public interface CustomerMapper {

	@Select("SELECT * FROM customers")
	public List<Customer> getCustomers();

	@Select("SELECT Id FROM customers")
	public List<Long> getCustomersIds();

	@Select("SELECT Name, Surname, Url, CreatedBy, ModifiedBy FROM customers WHERE id = #{id}")
	public Customer getCustomerInfo(Long id);

	@Insert("INSERT INTO customers(Name, Surname, Url, CreatedBy)"
			+ "VALUES (#{cus.Name}, #{cus.Surname}, #{cus.Url}, #{cus.CreatedBy})")
	public Integer createCustomer(Customer cus);

//    <foreach item="item" index="index" collection="list"
//            open="ID in (" separator="," close=")" nullable="true">
//              #{item}
//        </foreach>

	@Insert({ "<script>", "INSERT INTO customers(Name, Surname, Url, CreatedBy) VALUES",
			"		<foreach item='item' index='index' collection='list'",
			"       	 open='(' separator=',' close=')' nullable='false'>", "          #{item}", "    </foreach>",
			"</script>" })
	public Integer createCustomers(List<Customer> item);

	@Update({ "<script>", "update customers", "  <set>",
			"    <if test='customer.Name != null'>Name=#{customer.Name},</if>",
			"    <if test='customer.Surname != null'>Surname=#{customer.Surname},</if>",
			"    <if test='customer.Url != null'>Url=#{customer.Url},</if>",
			"    <if test='customer.Photo != null'>Photo=#{customer.Photo},</if>",
			"    <if test='customer.CreatedBy != null'>CreatedBy=#{customer.CreatedBy},</if>",
			"    <if test='customer.ModifiedBy != null'>ModifiedBy=#{customer.ModifiedBy.level}</if>", "  </set>",
			"where id=#{customer.id}", "</script>" })
	public Integer updateCustomer(Customer customer);

	@Delete("DELETE FROM customers WHERE id = #{id}")
	public Integer deleteCustomer(Long id);
}
