package com.oak.app;

/**
 * Created by Chennl on 6/23/2017.
 */

        import java.net.URI;
        import org.apache.tomcat.util.codec.binary.Base64;
        import org.springframework.http.HttpEntity;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpMethod;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.client.RestTemplate;
        import com.oak.model.Customer;
public class RestClientUtil {
    private HttpHeaders getHeaders() {
        String credential="mukesh:m123";
        //String credential="tarun:t123";
        String encodedCredential = new String(Base64.encodeBase64(credential.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedCredential);
        return headers;
    }
    public void getCustomerByIdDemo() {
        HttpHeaders headers = getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customer/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Customer.class, 1);
        Customer customer = responseEntity.getBody();
        System.out.println("Id:"+customer.getCustomerId()+", Name:"+customer.getCustomerName()
                +", Address:"+customer.getAddress());
    }
    public void getAllCustomersDemo() {
        HttpHeaders headers = getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customers";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Customer[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Customer[].class);
        Customer[] customers = responseEntity.getBody();
        for(Customer customer : customers) {
            System.out.println("Id:"+customer.getCustomerId()+", Name:"+customer.getCustomerName()
                    +", Address: "+customer.getAddress());
        }
    }
    public void addCustomerDemo() {
        HttpHeaders headers = getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customer";
        Customer objCustomer = new Customer();
        objCustomer.setCustomerName("Spring REST Security using Hibernate");
        objCustomer.setAddress("Spring");
        HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(objCustomer, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());
    }
    public void updateCustomerDemo() {
        HttpHeaders headers = getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customer";
        Customer objCustomer = new Customer();
        objCustomer.setCustomerId("0520000788");
        objCustomer.setCustomerName("Update:Java Concurrency");
        objCustomer.setAddress("Java");
        HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(objCustomer, headers);
        restTemplate.put(url, requestEntity);
    }
    public void deleteCustomerDemo() {
        HttpHeaders headers = getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customer/{id}";
        HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);
    }
    public static void main(String args[]) {
        RestClientUtil util = new RestClientUtil();
        //util.getCustomerByIdDemo();
        util.getAllCustomersDemo();
        //util.addCustomerDemo();
        util.updateCustomerDemo();
        //util.deleteCustomerDemo();
    }
}