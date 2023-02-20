package com.example.kotlinworkoutapp

object Constants {
    fun defaultExerList(): ArrayList<ExerciseModel>{
        val exerList = ArrayList<ExerciseModel>()

        val abdominalCrunch = ExerciseModel(
            1,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false)
        exerList.add(abdominalCrunch)

        val highKneesInPlace = ExerciseModel(
            2,
            "High Knees Running in Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false)
        exerList.add(highKneesInPlace)

        val jumpingJacks = ExerciseModel(
            3,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false)
        exerList.add(jumpingJacks)

        val lunge = ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge,
            false,
            false)
        exerList.add(lunge)

        val plank = ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank,
            false,
            false)
        exerList.add(plank)

        val pushUp = ExerciseModel(
            6,
            "Push Ups",
            R.drawable.ic_push_up,
            false,
            false)
        exerList.add(pushUp)

        val pushUpRot = ExerciseModel(
            7,
            "Push Up with Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false)
        exerList.add(pushUpRot)

        val sidePlank = ExerciseModel(
            8,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false)
        exerList.add(sidePlank)

        val squat = ExerciseModel(
            9,
            "Squats",
            R.drawable.ic_squat,
            false,
            false)
        exerList.add(squat)

        val stepUps = ExerciseModel(
            10,
            "Step Up onto Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false)
        exerList.add(stepUps)

        val tricepDip = ExerciseModel(
            11,
            "Tricep Chair dIPS",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false)
        exerList.add(tricepDip)

        val wallSits = ExerciseModel(
            12,
            "Wall Sits",
            R.drawable.ic_jumping_jacks,
            false,
            false)
        exerList.add(wallSits)

        return exerList
    }
}