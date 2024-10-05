# my-spring-security

## my-security

간단한 Spring Security 예제입니다.

## Filter

Spring Security 는 Filter 를 이용해 인증/인가를 수행합니다.
아래 코드로 생성된 Filter 를 확인해 볼 수 있습니다.

```java
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login"));

        SecurityFilterChain chain = http.build();
        List<Filter> filters = chain.getFilters();
        for (Filter filter : filters) {
            System.out.println(filter.getClass());
        }

        return chain;
    }
```

## UsernamePasswordAuthenticationFilter

사용자가 로그인 창에서 username 과 password 를 입력하게 되면,
`UsernamePasswordAuthenticationToken` 을 생성 후,
위 객체에 username 과 password 를 설정하고,
`AuthenticationManager` 에 해당 객체를 넘깁니다.

## AuthenticationManager

AuthenticationManager 는 `AuthenticationProvider` 를 찾습니다.
여기서는 `DaoAuthenticationProvider` 가 이용됩니다.

## UserDetailsService

본래는 DB 에서 username 을 찾아 비밀번호를 비교하는 로직이 있어야 하지만,
여기서는 소스를 간략화 하기 위해 매번 사용자정보를 생성하도록 작성하였습니다.