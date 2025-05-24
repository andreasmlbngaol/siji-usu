package com.sanalab.sijiusu.core.component;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.core.database.model.*;
import com.sanalab.sijiusu.core.database.repository.*;
import com.sanalab.sijiusu.siji_admin.database.model.Admin;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final HashEncoder hashEncoder;
    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public DataSeeder(
        UserRepository userRepository,
        HashEncoder hashEncoder,
        FacultyRepository facultyRepository,
        MajorRepository majorRepository,
        LecturerRepository lecturerRepository,
        StudentRepository studentRepository,
        CourseRepository courseRepository, CourseSectionRepository courseSectionRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.hashEncoder = hashEncoder;
        this.facultyRepository = facultyRepository;
        this.majorRepository = majorRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseSectionRepository = courseSectionRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) {
            seedFacultiesAndMajor();
            seedUsers();
        }
    }

    private void seedUsers() {
        var admin = new Admin();
        admin.setName("Admin");
        admin.setEmail("admin@example.com");
        admin.setPasswordHashed(hashEncoder.encode("test"));
        admin.setRole(Role.Admin);
        admin.setNip("admin");

        userRepository.save(admin);

        var computerScience = majorRepository.findByFaculty_FacultyCodeAndMajorCode("14", "01").orElseThrow();

        var amalia = new Lecturer();
        amalia.setName("Dr. Amalia, S.T., M.T.");
        amalia.setEmail("amalia@usu.ac.id");
        amalia.setPasswordHashed(hashEncoder.encode("test"));
        amalia.setRole(Role.Lecturer);
        amalia.setDepartment(computerScience);
        amalia.setNip("197812212014042001");
        amalia.setNidn("0121127801");

        amalia = lecturerRepository.save(amalia);

        var jos = new Lecturer();
        jos.setName("Dr. Jos Timanta Tarigan, S.Kom., M.Sc.");
        jos.setEmail("jostarigan@usu.ac.id");
        jos.setPasswordHashed(hashEncoder.encode("test"));
        jos.setRole(Role.Lecturer);
        jos.setDepartment(computerScience);
        jos.setNip("198501262015041001");
        jos.setNidn("0126018502");

        jos = lecturerRepository.save(jos);

        var andre = new Student();
        andre.setName("Andreas Manatar Lumban Gaol");
        andre.setEmail("lgandre45@gmail.com");
        andre.setPasswordHashed(hashEncoder.encode("test"));
        andre.setYear(2022);
        andre.setNim("221401067");
        andre.setRole(Role.Student);
        andre.setMajor(computerScience);
        andre.setAcademicAdvisor(amalia);

        studentRepository.save(andre);

        var room = new Room();
        room.setName("D-106");
        room.setDepartment(computerScience);

        var d106 = roomRepository.save(room);

        var course1 = new Course();
        course1.setName("Pemrograman Berorientasi Objek");
        course1.setMajor(computerScience);

        var pbo = courseRepository.save(course1);

        var course2 = new Course();
        course2.setName("Teori Bahasa Otomata");
        course2.setMajor(computerScience);

        var tbo = courseRepository.save(course2);

        var sections = List.of("A", "B", "C");

        for (var sectionName : sections) {
            var section1 = new CourseSection();
            section1.setName(sectionName);
            section1.setCourse(pbo);
            section1.setLecturer(jos);
            section1.setRoom(d106);

            courseSectionRepository.save(section1);

            var section2 = new CourseSection();
            section2.setName(sectionName);
            section2.setCourse(tbo);
            section2.setLecturer(amalia);
            section2.setRoom(d106);

            courseSectionRepository.save(section2);
        }
    }

    private record MajorSeed(String name, String majorCode) { }

    private record FacultySeed(String name, List<MajorSeed> majors) {}

    private void seedFacultiesAndMajor() {
        var faculties = List.of(
            new FacultySeed("Kedokteran", List.of(
                new MajorSeed("Pendidikan Dokter", "00")
            )),

            new FacultySeed("Hukum", List.of(
                new MajorSeed("Ilmu Hukum", "00")
            )),

            new FacultySeed("Pertanian", List.of(
                new MajorSeed("Agroteknologi", "01"),
                new MajorSeed("Manajemen Sumberdaya Perairan", "02"),
                new MajorSeed("Agribisnis", "03"),
                new MajorSeed("Teknologi Pangan", "05"),
                new MajorSeed("Peternakan", "06"),
                new MajorSeed("Teknik Pertanian dan Biosistem", "08"),
                new MajorSeed("Agroteknologi (PSDKU)", "10")
            )),

            new FacultySeed("Teknik", List.of(
                new MajorSeed("Teknik Mesin", "01"),
                new MajorSeed("Teknik Elektro", "02"),
                new MajorSeed("Teknik Industri", "03"),
                new MajorSeed("Teknik Sipil", "04"),
                new MajorSeed("Teknik Kimia", "05"),
                new MajorSeed("Arsitektur", "06"),
                new MajorSeed("Teknik Lingkungan", "07"),
                new MajorSeed("Pendidikan Profesi Insinyur", "31")
            )),

            new FacultySeed("Ekonomi dan Bisnis", List.of(
                new MajorSeed("Ekonomi Pembangunan", "01"),
                new MajorSeed("Manajemen", "02"),
                new MajorSeed("Akuntansi", "03"),
                new MajorSeed("Kewirausahaan", "04")
            )),

            new FacultySeed("Kedokteran Gigi", List.of(
                new MajorSeed("Sarjana Kedokteran Gigi", "00"),
                new MajorSeed("Profesi Kedokteran Gigi", "31")
            )),

            new FacultySeed("Ilmu Budaya", List.of(
                new MajorSeed("Sastra Indonesia", "01"),
                new MajorSeed("Sastra Melayu", "02"),
                new MajorSeed("Sastra Batak", "03"),
                new MajorSeed("Sastra Arab", "04"),
                new MajorSeed("Sastra Inggris", "05"),
                new MajorSeed("Ilmu Sejarah", "06"),
                new MajorSeed("Etnomusikologi", "07"),
                new MajorSeed("Sastra Jepang", "08"),
                new MajorSeed("Perpustakaan dan Sains Informasi", "09"),
                new MajorSeed("Bahasa Mandarin", "10")
            )),

            new FacultySeed("Matematika dan Ilmu Pengetahuan Alam", List.of(
                new MajorSeed("Fisika", "01"),
                new MajorSeed("Kimia", "02"),
                new MajorSeed("Matematika", "03"),
                new MajorSeed("Biologi", "05")
            )),

            new FacultySeed("Ilmu Sosial dan Politik", List.of(
                new MajorSeed("Sosiologi", "01"),
                new MajorSeed("Ilmu Kesejahteraan Sosial", "02"),
                new MajorSeed("Ilmu Administrasi Publik", "03"),
                new MajorSeed("Ilmu Komunikasi", "04"),
                new MajorSeed("Antropologi Sosial", "05"),
                new MajorSeed("Ilmu Politik", "06"),
                new MajorSeed("Ilmu Administrasi Bisnis", "07")
            )),

            new FacultySeed("Kesehatan Masyarakat", List.of(
                new MajorSeed("Kesehatan Masyarakat", "00"),
                new MajorSeed("Gizi", "01")
            )),

            new FacultySeed("Keperawatan", List.of(
                new MajorSeed("Sarjana Keperawatan", "01"),
                new MajorSeed("Profesi Ners", "02")
            )),

            new FacultySeed("Kehutanan", List.of(
                new MajorSeed("Kehutanan", "01")
            )),

            new FacultySeed("Psikologi", List.of(
                new MajorSeed("Psikologi", "01")
            )),

            new FacultySeed("Ilmu Komputer dan Teknologi Informasi", List.of(
                new MajorSeed("Ilmu Komputer", "01"),
                new MajorSeed("Teknologi Informasi", "02")
            )),

            new FacultySeed("Farmasi", List.of(
                new MajorSeed("Farmasi", "01")
            ))
        );

        for (int i = 0; i < faculties.size(); i++) {
            var facultySeed = faculties.get(i);

            var faculty = new Faculty();
            faculty.setName(facultySeed.name);
            faculty.setFacultyCode(String.format("%02d", i + 1));

            var res = facultyRepository.save(faculty);

            facultySeed.majors.forEach(majorSeed -> {
                var major = new Major();
                major.setName(majorSeed.name);
                major.setMajorCode(majorSeed.majorCode);
                major.setFaculty(res);

                majorRepository.save(major);
            });
        }
    }
}