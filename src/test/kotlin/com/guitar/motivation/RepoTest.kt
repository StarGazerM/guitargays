package com.guitar.motivation

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RepoTest(@Autowired val entityManager: TestEntityManager,
               @Autowired val userRepo: UserRepo,
               @Autowired val fileStoredRepo: FileStoredRepo) {


    @BeforeAll
    fun startup(){
        print(">> start test jpa repo")
    }

    @Test
    fun `when_find_user_by_id` (){
        val u = User(null,true, "test user","test",
                "1", "fake", null, mutableListOf())
        val uid = entityManager.persistAndGetId(u) as Long
        entityManager.flush()

        val found = userRepo.findById(uid)
        assert(found.get().username == "test user")
    }

    @Test
    fun `when_find_a_file_by_its_owner_id` () {
        val u = User(null,true, "test user","test",
                "1", "fake", null, mutableListOf())
        val m = FileStored(null,"test media","/a/a", u, AUDIO)

        val uid = entityManager.persistAndGetId(u) as Long
        val fid = entityManager.persistAndGetId(m) as Long
        // fileStoredRepo.save(m)
        entityManager.flush()

        val foundM = fileStoredRepo.findById(fid).get()
        u.files.add(m)
        userRepo.save(u)


        val found = userRepo.findById(uid).get().files
        assert(found.isNotEmpty())
    }

//    @Test
//    fun `when find a file by it's owner id and type` () {
//        val found = fileStoredRepo.findByUserIdAndType(uid, AUDIO)
//        assert(found.isNotEmpty())
//    }
}