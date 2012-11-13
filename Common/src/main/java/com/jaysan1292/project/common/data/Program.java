package com.jaysan1292.project.common.data;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;

public class Program extends BaseEntity<Program> implements Comparable<Program> {
    public static final HashMap<String, Program> AllPrograms = new HashMap<String, Program>() {{
        put("A101", new Program(0, "A101", "College Vocational"));
        put("A102", new Program(1, "A102", "Pre-Health Science"));
        put("A103", new Program(2, "A103", "Pre-Community Services"));
        put("A105", new Program(3, "A105", "Assistant Cook Extended Training"));
        put("A106", new Program(4, "A106", "Construction Craft Worker Foundations"));
        put("A146", new Program(5, "A146", "Pre-Business"));
        put("A737", new Program(6, "A737", "Academic Upgrading"));
        put("A738", new Program(7, "A738", "Academic Upgrading for Deaf and Hard-of-Hearing Adults"));
        put("B103", new Program(8, "B103", "Business - Accounting"));
        put("B107", new Program(9, "B107", "Business Administration - Accounting"));
        put("B108", new Program(10, "B108", "Business Administration - Marketing"));
        put("B130", new Program(11, "B130", "Business Administration"));
        put("B144", new Program(12, "B144", "Business Administration - Human Resources (no co-op)"));
        put("B145", new Program(13, "B145", "Business Administration (no co-op)"));
        put("B150", new Program(14, "B150", "Business Administration"));
        put("B154", new Program(15, "B154", "Business Administration - Human Resources (with co-op)"));
        put("B155", new Program(16, "B155", "Business Administration (with co-op)"));
        put("B157", new Program(17, "B157", "Business Administration - Accounting(with co-op)"));
        put("B158", new Program(18, "B158", "Business Administration - Marketing (with co-op)"));
        put("B301", new Program(19, "B301", "Bachelor of Applied Business - Financial Services"));
        put("B400", new Program(20, "B400", "Sport and Event Marketing (Postgraduate)"));
        put("B406", new Program(21, "B406", "Marketing Management - Financial Services (Postgraduate)"));
        put("B407", new Program(22, "B407", "Financial Planning (Postgraduate)"));
        put("B408", new Program(23, "B408", "Human Resources Management (Postgraduate)"));
        put("B409", new Program(24, "B409", "Strategic Relationship Marketing (Postgraduate)"));
        put("B410", new Program(25, "B410", "Small Business Entrepreneurship (Postgraduate)"));
        put("B411", new Program(26, "B411", "International Business Management (Postgraduate)"));
        put("C100", new Program(27, "C100", "Early Childhood Education"));
        put("C101", new Program(28, "C101", "Community Worker"));
        put("C102", new Program(29, "C102", "Activation Co-ordinator"));
        put("C104", new Program(30, "C104", "Child and Youth Worker"));
        put("C105", new Program(31, "C105", "Early Childhood Assistant"));
        put("C108", new Program(32, "C108", "Intervenor for Deaf-Blind Persons"));
        put("C109", new Program(33, "C109", "Career and Work Counsellor"));
        put("C110", new Program(34, "C110", "American Sign Language - English Interpreter"));
        put("C112", new Program(35, "C112", "Personal Support Worker"));
        put("C114", new Program(36, "C114", "American Sign Language and Deaf Studies"));
        put("C115", new Program(37, "C115", "Office Administration - Medical"));
        put("C116", new Program(38, "C116", "Behavioural Science Technology"));
        put("C118", new Program(39, "C118", "Early Childhood Education (Consecutive Diploma)"));
        put("C119", new Program(40, "C119", "Social Service Worker"));
        put("C129", new Program(41, "C129", "Career and Work Counsellor (for Internationally Educated Professionals)"));
        put("C134", new Program(42, "C134", "Child and Youth Worker (Fast Track)"));
        put("C135", new Program(43, "C135", "Social Service Worker (Fast Track)"));
        put("C138", new Program(44, "C138", "Career and Work Counsellor (Fast Track)"));
        put("C139", new Program(45, "C139", "Health Information Management"));
        put("C300", new Program(46, "C300", "Early Childhood Leadership (Bachelor of Applied Arts Degree Program)"));
        put("C301", new Program(47, "C301", "Early Childhood Leadership (Bachelor of Applied Arts Degree Program) Fast Track"));
        put("C405", new Program(48, "C405", "Autism and Behavioural Science (Postgraduate)"));
        put("C701", new Program(49, "C701", "Redirection Through Education - For You"));
        put("C702", new Program(50, "C702", "Redirection Through Education - Work Entry"));
        put("F102", new Program(51, "F102", "Fashion Management"));
        put("F105", new Program(52, "F105", "Gemmology"));
        put("F110", new Program(53, "F110", "Jewellery Methods"));
        put("F111", new Program(54, "F111", "Jewellery Essentials"));
        put("F112", new Program(55, "F112", "Fashion Business Industry"));
        put("F113", new Program(56, "F113", "Fashion Techniques and Design"));
        put("F114", new Program(57, "F114", "Jewellery Arts"));
        put("F402", new Program(58, "F402", "International Fashion Development and Management (Postgraduate)"));
        put("G102", new Program(59, "G102", "Graphic Design"));
        put("G108", new Program(60, "G108", "Art and Design Foundation"));
        put("G109", new Program(61, "G109", "Game Development"));
        put("G401", new Program(62, "G401", "Design Management (Postgraduate)"));
        put("G402", new Program(63, "G402", "Digital Design - Advanced Digital Design (Postgraduate)"));
        put("G405", new Program(64, "G405", "Digital Design - Game Design (Postgraduate)"));
        put("G414", new Program(65, "G414", "Interdisciplinary Design Strategy (Postgraduate)"));
        put("H100", new Program(66, "H100", "Culinary Management"));
        put("H101", new Program(67, "H101", "Hospitality Services"));
        put("H102", new Program(68, "H102", "Food and Beverage Management"));
        put("H103", new Program(69, "H103", "Hotel Management"));
        put("H108", new Program(70, "H108", "Baking Pre-Employment"));
        put("H110", new Program(71, "H110", "Hospitality"));
        put("H112", new Program(72, "H112", "Culinary Skills - Chef Training"));
        put("H113", new Program(73, "H113", "Baking and Pastry Arts Management"));
        put("H116", new Program(74, "H116", "Culinary Management (Integrated Learning)"));
        put("H119", new Program(75, "H119", "Culinary Management - Nutrition"));
        put("H121", new Program(76, "H121", "Special Events Planning"));
        put("H301", new Program(77, "H301", "Bachelor of Applied Business - Hospitality Operations Management"));
        put("H402", new Program(78, "H402", "Food and Nutrition Management"));
        put("H411", new Program(79, "H411", "Culinary Arts - Italian (Postgraduate)"));
        put("P100", new Program(80, "P100", "Theatre Arts"));
        put("P101", new Program(81, "P101", "Dance Performance Preparation"));
        put("P102", new Program(82, "P102", "Dance Performance Studies"));
        put("P103", new Program(83, "P103", "Commercial Dance Studies"));
        put("R101", new Program(84, "R101", "General Arts and Science - Two-Year"));
        put("R102", new Program(85, "R102", "General Arts and Science - Introduction to Performing Arts Careers"));
        put("R104", new Program(86, "R104", "General Arts and Science One-Year"));
        put("R106", new Program(87, "R106", "Northern Women"));
        put("R107", new Program(88, "R107", "Assaulted Women"));
        put("R400", new Program(89, "R400", "Teaching English as a Second Language to Adults (TESL)"));
        put("R403", new Program(90, "R403", "College Teacher Training (for Internationally Educated Professionals) (Postgraduate)"));
        put("R732", new Program(91, "R732", "Intensive English as a Second Language (ESL)"));
        put("R739", new Program(92, "R739", "Intensive English as a Second Language for International Students (ESL)"));
        put("S100", new Program(93, "S100", "Dental Technology"));
        put("S101", new Program(94, "S101", "Denturism"));
        put("S102", new Program(95, "S102", "Orthotic"));
        put("S113", new Program(96, "S113", "Dental Assisting Levels I and II"));
        put("S115", new Program(97, "S115", "Dental Office Administration"));
        put("S117", new Program(98, "S117", "Hearing Instrument Specialist"));
        put("S118", new Program(99, "S118", "Bachelor of Science in Nursing (B"));
        put("S119", new Program(100, "S119", "Personal Support Worker Pathway to Practical Nursing"));
        put("S121", new Program(101, "S121", "Practical Nursing"));
        put("S122", new Program(102, "S122", "R"));
        put("S124", new Program(103, "S124", "Dental Hygiene"));
        put("S125", new Program(104, "S125", "Fitness and Health Promotion"));
        put("S400", new Program(105, "S400", "Restorative Dental Hygiene (Postgraduate)"));
        put("S402", new Program(106, "S402", "Registered Nurse - Critical Care Nursing (Postgraduate)"));
        put("S404", new Program(107, "S404", "Registered Nurse - Perinatal Intensive Care Nursing (Postgraduate)"));
        put("S407", new Program(108, "S407", "Clinical Methods in Orthotics"));
        put("S414", new Program(109, "S414", "Registered Nurse - Operating Room Perioperative Nursing (Postgraduate)"));
        put("S415", new Program(110, "S415", "Registered Nurse - Family Practice Nursing"));
        put("S416", new Program(111, "S416", "Interprofessional Acute Care Paediatric Cardiology Certificate"));
        put("S422", new Program(112, "S422", "Registered Nurse - Critical Care Nursing (Online) (Postgraduate)"));
        put("T105", new Program(113, "T105", "Construction Engineering Technology"));
        put("T109", new Program(114, "T109", "Architectural Technology"));
        put("T110", new Program(115, "T110", "Building Renovation Technician"));
        put("T121", new Program(116, "T121", "Mechanical Engineering Technology - Design"));
        put("T126", new Program(117, "T126", "Construction Trade Techniques"));
        put("T127", new Program(118, "T127", "Computer Programmer Analyst"));
        put("T132", new Program(119, "T132", "Architectural Technician"));
        put("T141", new Program(120, "T141", "Computer Systems Technician"));
        put("T143", new Program(121, "T143", "Mechanical Technician - Tool"));
        put("T146", new Program(122, "T146", "Electromechanical Engineering Technician"));
        put("T147", new Program(123, "T147", "Computer Systems Technology"));
        put("T148", new Program(124, "T148", "Building Renovation Technology"));
        put("T151", new Program(125, "T151", "Railway Conductor"));
        put("T160", new Program(126, "T160", "Heating"));
        put("T161", new Program(127, "T161", "Construction Engineering Technician"));
        put("T162", new Program(128, "T162", "Heating"));
        put("T163", new Program(129, "T163", "Game Programming"));
        put("T164", new Program(130, "T164", "Civil Engineering Technology"));
        put("T167", new Program(131, "T167", "Electrical Techniques"));
        put("T302", new Program(132, "T302", "Bachelor of Applied Technology - Construction Science and Management"));
        put("T402", new Program(133, "T402", "Health Informatics (Postgraduate)"));
        put("T403", new Program(134, "T403", "Construction Management for Internationally Educated Professionals (Postgraduate)"));
        put("T405", new Program(135, "T405", "Information Systems Business Analyst (Postgraduate)"));
        put("T411", new Program(136, "T411", "Wireless Networking (Postgraduate)"));
        put("T901", new Program(137, "T901", "Electronics Technician (Distance Education)"));
        put("T902", new Program(138, "T902", "Electromechanical Technician (Distance Education)"));
        put("T948", new Program(139, "T948", "Robotics Technician (Distance Education)"));
    }};
    private long programId;
    private String programCode;
    private String programName;

    public Program() {
        this(-1, "(null)", "(null)");
    }

    private Program(long programId, String code, String name) {
        this.programId = programId;
        this.programCode = code;
        this.programName = name;
    }

    public Program(Program other) {
        this(other.programId, other.programCode, other.programName);
    }

    //region Getters and Setters

    public long getId() {
        return programId;
    }

    public String getProgramCode() {
        return programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setId(long id) {
        this.programId = id;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    //endregion

    @Override
    public String toString() {
        return programCode + ": " + programName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Program)) return false;
        Program other = (Program) obj;
        return programCode.equals(other.programCode) &&
               programName.equals(other.programName);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 97)
                .append(programCode)
                .append(programName)
                .toHashCode();
    }

    @Override
    public int compareTo(Program o) {
        return programCode.compareTo(o.programCode);
    }
}
