package com.example.search_app.capston.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Company {
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String companyAddress;
    @NonNull
    String phone;









}
