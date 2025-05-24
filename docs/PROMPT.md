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
10. ```
    @Entity(name = "users")
    public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //    @Column(nullable = false)
    //    private String email;
    
        @Column(nullable = false, name = "name")
        private String name;
    
        @Column(name = "password_hashed")
        private String passwordHashed;
    
        @Column(nullable = false, name = "created_at")
        private Instant createdAt;
    
        @Column(nullable = false, name = "updated_at")
        private Instant updatedAt;
    
        @Column(nullable = false, name = "role")
        @Enumerated(EnumType.STRING)
        private Role role = Role.Student;
    }
    ```
    Apakah disini Role.Student ini adalah default value? Apa bisa gitu dengan hibernate di Java?
11. ```
    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("type", type)
        .issuedAt(now)
        .expiration(expiration)
        .signWith(secretKey, Jwts.SIG.HS256)
        .compact();
    ```
    
    Di Jwt apakah ada memberikan data tambahan? Contoh saya ingin memberikan data Role
12. ```
    @Entity(name = "courseSections")
    public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
        @Column(nullable = false, unique = true)
        private String name;
    
        @JoinColumn
        @OneToOne(fetch = FetchType.EAGER)
        private Lecturer lecturer;
    
        @JoinColumn
        @OneToOne(fetch = FetchType.EAGER)
        private Room room;
    
        @Column
        @OneToMany(fetch = FetchType.LAZY)
        private List<Student> students;
    
        @JoinColumn
        @OneToOne(fetch = FetchType.EAGER)
        private Course course;
    }
    
    @Entity(name = "students")
    public class Student extends User {
    @Column(nullable = false, unique = true)
    private String nim;
    
        @JoinColumn(nullable = false)
        @OneToOne(fetch = FetchType.EAGER)
        private Major major;
    
        @Column(nullable = false)
        @OneToMany(fetch = FetchType.LAZY)
        private List<Course> courses;
    
        @JoinColumn(nullable = false, name = "academic_advisor")
        @OneToOne(fetch = FetchType.EAGER)
        private Lecturer academicAdvisor;
    }
    ```
    
    Kenapa dia membuat sebuah tabel sections_students yang isinya ada 2 column yaitu student_id dan section_id?
13. Kenapa bisa ada ManyToMany? Kan kalau ditinjau lagi, satu student punya banyak courses, dan 1 courses punya banyak students, jadi harusnya bukannya OneToMany di kedua sisi?
14. Misal sebuah Course baru dibuat, kan state awalnya harusnya dia tidak punya section kan?
    Itu apakah berarti nullable true? Atau karena dia adalah List<Course> tidak null?
15. Apakah jika saya pakai NoSQL database dengan JPA dia tetap bisa membaut @OneToMany dan lainnya?
16. Kalau begini, sebuah fakultas punya banyak department, dan di sebuah department ada banyak dosen. Apakah dosen perlu punya relasi ke fakultas atau cukup dengan department saja dan department yang berhubungan dengan fakultas?
17. Jelaskan tentang softDelete di JPA!
18. Kalau saya punya class Student yang extend User.
    lalu saya punya interface UserRepository yang extends interface JpaRepository<User, Long>, untuk StudentRepository apakah harusnya extends UserRepository atau tetap extends JpaRepository?
19. Nah misal di UserRepository nya saya buat findByEmail, apakah di StudentRepository nya perlu definisikan lagi?
20. Tadi maslaah soal infinite loop saat fetch bisa diselesaikan dengan fetch lazy kan. Nah selanjutnya, contoh kalau masuk ke halaman lecturer, lalu lecturer ingin mengambil daftar student bimbingannya, kan otomatis field lazy ini akan ke trigger kan, lalu di student ada lecturer sebagai academicAdvisor lagi (eager) apakah ini aman secara performa?
21. Misal saja pakai @JsonBackReference dan @JsonManagedReference lah, saat saya memang hanya mau mengirimkan sebuah object Student, apakah dia akan mengirimkan academicAdvisor nya?
22. Jadi bagusan tetap buat DTO saja ya?
23. Misal jika ada mahasiswa baru, tugas admin lah untuk menambahkan nya, apakah ini seharusnya ada di AdminController atau StudentController, dan endpoint apa yang cocok?
24. Misal begitu, kan nanti admin masih akan ada banyak sekali endpoint, kalau semua diletakkan di 1 class ini apa tidak kacau, atau bisa membuat pemisahan tanggung jawab sedikit, seperti mengkategorikan ManageUser yang isinya /users/students dan /users/teachers?
25. Ini lebih sesuai dengan yang ada di pikiran saya, tetapi saya pernah pakai Ktor Server, dan disitu dia bisa route { route {}}
    Jadi walau dia modular, tapi semua masih di dalam route itu, sedangkan disini kan kamu buat dia terpisah seperti semua Controller itu tidak memiliki 1 pusat yaitu AdminController
26. Okelah pakai seperti sebelumnya saja, mungkin cara agar seolah olah satu route bisa dengan menyatukan di dalam 1 package aja ya
27. Apakah di spring boot ini bisa seperti seed data ke databasenya? Contoh saya ingin membuat sebuah admin untuk menambah semua data nantinya
28. Apakah ada method untuk cek String.isEmail() gitu?
29. Bukan, saya hanya mau cek sebuah string email atau tidak nya. Karena untuk login bisa pakai nim, nip, nidn, email. Jadi seperti mengecek identifier nya
30. Kenapa saat saya coba akses ini:
    ```
        public record LoginRequest(
            String identifier,
            String password
        ) {}
    
        @PostMapping("/login")
        public AuthService.TokenPair login(
            @RequestBody LoginRequest body
        ) {
            return authService.login(
                body.identifier,
                body.password
            );
        }
    
    ```
    Dia malah 500 saat saya hanya mengirimkan password, tanpa identifier. Bukannya seharusnya bad request?
31. Cara cek string adalah numerik?
32. Bagaimaan pakai switch penggantin if else nya? Saya memang bikin perilaku seperti when di kotlin, tapi switch perlu parameter
33. Apakah ada forEachIndexed di Java?
34. 