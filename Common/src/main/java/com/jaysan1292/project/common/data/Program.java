package com.jaysan1292.project.common.data;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;

public class Program extends BaseEntity<Program> implements Comparable<Program> {
    public static final HashMap<String, Program> AllPrograms = new HashMap<String, Program>() {{
        put("A101", new Program("A101", "College Vocational"));
        put("A102", new Program("A102", "Pre-Health Science"));
        put("A103", new Program("A103", "Pre-Community Services"));
        put("A105", new Program("A105", "Assistant Cook Extended Training"));
        put("A106", new Program("A106", "Construction Craft Worker Foundations"));
        put("A146", new Program("A146", "Pre-Business"));
        put("A737", new Program("A737", "Academic Upgrading"));
        put("A738", new Program("A738", "Academic Upgrading for Deaf and Hard-of-Hearing Adults"));
        put("B103", new Program("B103", "Business - Accounting"));
        put("B107", new Program("B107", "Business Administration - Accounting"));
        put("B108", new Program("B108", "Business Administration - Marketing"));
        put("B130", new Program("B130", "Business Administration"));
        put("B144", new Program("B144", "Business Administration - Human Resources (no co-op)"));
        put("B145", new Program("B145", "Business Administration (no co-op)"));
        put("B150", new Program("B150", "Business Administration"));
        put("B154", new Program("B154", "Business Administration - Human Resources (with co-op)"));
        put("B155", new Program("B155", "Business Administration (with co-op)"));
        put("B157", new Program("B157", "Business Administration - Accounting(with co-op)"));
        put("B158", new Program("B158", "Business Administration - Marketing (with co-op)"));
        put("B301", new Program("B301", "Bachelor of Applied Business - Financial Services"));
        put("B400", new Program("B400", "Sport and Event Marketing (Postgraduate)"));
        put("B406", new Program("B406", "Marketing Management - Financial Services (Postgraduate)"));
        put("B407", new Program("B407", "Financial Planning (Postgraduate)"));
        put("B408", new Program("B408", "Human Resources Management (Postgraduate)"));
        put("B409", new Program("B409", "Strategic Relationship Marketing (Postgraduate)"));
        put("B410", new Program("B410", "Small Business Entrepreneurship (Postgraduate)"));
        put("B411", new Program("B411", "International Business Management (Postgraduate)"));
        put("C100", new Program("C100", "Early Childhood Education"));
        put("C101", new Program("C101", "Community Worker"));
        put("C102", new Program("C102", "Activation Co-ordinator"));
        put("C104", new Program("C104", "Child and Youth Worker"));
        put("C105", new Program("C105", "Early Childhood Assistant"));
        put("C108", new Program("C108", "Intervenor for Deaf-Blind Persons"));
        put("C109", new Program("C109", "Career and Work Counsellor"));
        put("C110", new Program("C110", "American Sign Language - English Interpreter"));
        put("C112", new Program("C112", "Personal Support Worker"));
        put("C114", new Program("C114", "American Sign Language and Deaf Studies"));
        put("C115", new Program("C115", "Office Administration - Medical"));
        put("C116", new Program("C116", "Behavioural Science Technology"));
        put("C118", new Program("C118", "Early Childhood Education (Consecutive Diploma)"));
        put("C119", new Program("C119", "Social Service Worker"));
        put("C129", new Program("C129", "Career and Work Counsellor (for Internationally Educated Professionals)"));
        put("C134", new Program("C134", "Child and Youth Worker (Fast Track)"));
        put("C135", new Program("C135", "Social Service Worker (Fast Track)"));
        put("C138", new Program("C138", "Career and Work Counsellor (Fast Track)"));
        put("C139", new Program("C139", "Health Information Management"));
        put("C300", new Program("C300", "Early Childhood Leadership (Bachelor of Applied Arts Degree Program)"));
        put("C301", new Program("C301", "Early Childhood Leadership (Bachelor of Applied Arts Degree Program) Fast Track"));
        put("C405", new Program("C405", "Autism and Behavioural Science (Postgraduate)"));
        put("C701", new Program("C701", "Redirection Through Education - For You"));
        put("C702", new Program("C702", "Redirection Through Education - Work Entry"));
        put("F102", new Program("F102", "Fashion Management"));
        put("F105", new Program("F105", "Gemmology"));
        put("F110", new Program("F110", "Jewellery Methods"));
        put("F111", new Program("F111", "Jewellery Essentials"));
        put("F112", new Program("F112", "Fashion Business Industry"));
        put("F113", new Program("F113", "Fashion Techniques and Design"));
        put("F114", new Program("F114", "Jewellery Arts"));
        put("F402", new Program("F402", "International Fashion Development and Management (Postgraduate)"));
        put("G102", new Program("G102", "Graphic Design"));
        put("G108", new Program("G108", "Art and Design Foundation"));
        put("G109", new Program("G109", "Game Development"));
        put("G401", new Program("G401", "Design Management (Postgraduate)"));
        put("G402", new Program("G402", "Digital Design - Advanced Digital Design (Postgraduate)"));
        put("G405", new Program("G405", "Digital Design - Game Design (Postgraduate)"));
        put("G414", new Program("G414", "Interdisciplinary Design Strategy (Postgraduate)"));
        put("H100", new Program("H100", "Culinary Management"));
        put("H101", new Program("H101", "Hospitality Services"));
        put("H102", new Program("H102", "Food and Beverage Management"));
        put("H103", new Program("H103", "Hotel Management"));
        put("H108", new Program("H108", "Baking Pre-Employment"));
        put("H110", new Program("H110", "Hospitality"));
        put("H112", new Program("H112", "Culinary Skills - Chef Training"));
        put("H113", new Program("H113", "Baking and Pastry Arts Management"));
        put("H116", new Program("H116", "Culinary Management (Integrated Learning)"));
        put("H119", new Program("H119", "Culinary Management - Nutrition"));
        put("H121", new Program("H121", "Special Events Planning"));
        put("H301", new Program("H301", "Bachelor of Applied Business - Hospitality Operations Management"));
        put("H402", new Program("H402", "Food and Nutrition Management"));
        put("H411", new Program("H411", "Culinary Arts - Italian (Postgraduate)"));
        put("P100", new Program("P100", "Theatre Arts"));
        put("P101", new Program("P101", "Dance Performance Preparation"));
        put("P102", new Program("P102", "Dance Performance Studies"));
        put("P103", new Program("P103", "Commercial Dance Studies"));
        put("R101", new Program("R101", "General Arts and Science - Two-Year"));
        put("R102", new Program("R102", "General Arts and Science - Introduction to Performing Arts Careers"));
        put("R104", new Program("R104", "General Arts and Science One-Year"));
        put("R106", new Program("R106", "Northern Women"));
        put("R107", new Program("R107", "Assaulted Women"));
        put("R400", new Program("R400", "Teaching English as a Second Language to Adults (TESL)"));
        put("R403", new Program("R403", "College Teacher Training (for Internationally Educated Professionals) (Postgraduate)"));
        put("R732", new Program("R732", "Intensive English as a Second Language (ESL)"));
        put("R739", new Program("R739", "Intensive English as a Second Language for International Students (ESL)"));
        put("S100", new Program("S100", "Dental Technology"));
        put("S101", new Program("S101", "Denturism"));
        put("S102", new Program("S102", "Orthotic"));
        put("S113", new Program("S113", "Dental Assisting Levels I and II"));
        put("S115", new Program("S115", "Dental Office Administration"));
        put("S117", new Program("S117", "Hearing Instrument Specialist"));
        put("S118", new Program("S118", "Bachelor of Science in Nursing (B"));
        put("S119", new Program("S119", "Personal Support Worker Pathway to Practical Nursing"));
        put("S121", new Program("S121", "Practical Nursing"));
        put("S122", new Program("S122", "R"));
        put("S124", new Program("S124", "Dental Hygiene"));
        put("S125", new Program("S125", "Fitness and Health Promotion"));
        put("S400", new Program("S400", "Restorative Dental Hygiene (Postgraduate)"));
        put("S402", new Program("S402", "Registered Nurse - Critical Care Nursing (Postgraduate)"));
        put("S404", new Program("S404", "Registered Nurse - Perinatal Intensive Care Nursing (Postgraduate)"));
        put("S407", new Program("S407", "Clinical Methods in Orthotics"));
        put("S414", new Program("S414", "Registered Nurse - Operating Room Perioperative Nursing (Postgraduate)"));
        put("S415", new Program("S415", "Registered Nurse - Family Practice Nursing"));
        put("S416", new Program("S416", "Interprofessional Acute Care Paediatric Cardiology Certificate"));
        put("S422", new Program("S422", "Registered Nurse - Critical Care Nursing (Online) (Postgraduate)"));
        put("T105", new Program("T105", "Construction Engineering Technology"));
        put("T109", new Program("T109", "Architectural Technology"));
        put("T110", new Program("T110", "Building Renovation Technician"));
        put("T121", new Program("T121", "Mechanical Engineering Technology - Design"));
        put("T126", new Program("T126", "Construction Trade Techniques"));
        put("T127", new Program("T127", "Computer Programmer Analyst"));
        put("T132", new Program("T132", "Architectural Technician"));
        put("T141", new Program("T141", "Computer Systems Technician"));
        put("T143", new Program("T143", "Mechanical Technician - Tool"));
        put("T146", new Program("T146", "Electromechanical Engineering Technician"));
        put("T147", new Program("T147", "Computer Systems Technology"));
        put("T148", new Program("T148", "Building Renovation Technology"));
        put("T151", new Program("T151", "Railway Conductor"));
        put("T160", new Program("T160", "Heating"));
        put("T161", new Program("T161", "Construction Engineering Technician"));
        put("T162", new Program("T162", "Heating"));
        put("T163", new Program("T163", "Game Programming"));
        put("T164", new Program("T164", "Civil Engineering Technology"));
        put("T167", new Program("T167", "Electrical Techniques"));
        put("T302", new Program("T302", "Bachelor of Applied Technology - Construction Science and Management"));
        put("T402", new Program("T402", "Health Informatics (Postgraduate)"));
        put("T403", new Program("T403", "Construction Management for Internationally Educated Professionals (Postgraduate)"));
        put("T405", new Program("T405", "Information Systems Business Analyst (Postgraduate)"));
        put("T411", new Program("T411", "Wireless Networking (Postgraduate)"));
        put("T901", new Program("T901", "Electronics Technician (Distance Education)"));
        put("T902", new Program("T902", "Electromechanical Technician (Distance Education)"));
        put("T948", new Program("T948", "Robotics Technician (Distance Education)"));
    }};
    private long programId;
    private String programCode;
    private String programName;

    public Program() {
        this("(null)", "(null)");
    }

    private Program(String code, String name) {
        this.programCode = code;
        this.programName = name;
    }

    public Program(Program other) {
        this(other.programCode, other.programName);
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
