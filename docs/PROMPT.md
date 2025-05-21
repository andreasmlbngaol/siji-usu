# Prompt List
 
1. Apakah untuk membuat RestAPI dengan Spring Boot harus pakai Spring Web?
2. Jika saya mau menggunakan PostgreSQL, dan JPA sebagai ORM, apakah saya perlu include dependency JDBC API? Atau cukup dengan Spring Data JPA dan PostgreSQL Driver?
3. Apa fungsi Validation (Bean Validation with Hibernate Validator)?
4. Apa perbedaan `stringA == stringB` dan `stringA.equals(stringB)`?
5. Di kotlin saya melakukan ini:
    ```
    @Component
    class JwtAuthFilter(
        private val jwtService: JwtService
    ): OncePerRequestFilter() {
    ```
    
    Kalau di java jadi gimana?
6. class JwtService saya merupakan @Service
7. ```
    @Service
    public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    ```
    
    Disini property jwtSecret saya merupakan value yang diambil dari application.yaml. Apakah ini sekarang saya masih harus membuat constructor nya lagi?
8. Kalau gitu kenapa di JwtAuthFilter harus dibuat constructor untuk memasukkan JwtService nya?
    ```
    @Component
    public class JwtAuthFilter extends OncePerRequestFilter {
    
        private final JwtService jwtService;
    
        public JwtAuthFilter(JwtService jwtService) {
            this.jwtService = jwtService;
        }
    ```
9. ```
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/auth/**").permitAll()
                    .dispatcherTypeMatchers(
                        DispatcherType.ERROR,
                        DispatcherType.FORWARD
                    ).permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling( configurer ->
                configurer
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .addFilterBefore(authFilter, OncePerRequestFilter.class)
            .build();
   ```
   
   Ini .exceptionHandling() gunanya apa?