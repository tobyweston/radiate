// proguard - reduce 30 MB artifact to something like 5.5 MB (https://github.com/sbt/sbt-proguard)

enablePlugins(SbtProguard)

proguardOptions in Proguard ++= Seq(
  "-dontnote",
  "-dontwarn scala.**",
  "-dontwarn com.google.code.tempusfugit.**",
  "-dontwarn org.apache.**",
  "-dontwarn scodec.bits.**",
  "-dontwarn scalaparsers.**",
  "-ignorewarnings",
  "-dontobfuscate",
  "-printusage unused-code.txt",
  """
    -keep public class bad.robot.** {
      *;
    }

    -keep public class org.apache.commons.** {
      *;
     }

    -keepclassmembers class * {
      ** MODULE$;
    }

  """
)

proguardOptions in Proguard += ProguardOptions.keepMain("bad.robot.radiate.Main")

proguardInputs in Proguard := (dependencyClasspath in Compile).value.files

proguardFilteredInputs in Proguard ++= ProguardOptions.noFilter((packageBin in Compile).value)

javaOptions in(Proguard, proguard) := Seq("-Xmx2G")       // avoids out of memory (https://github.com/sbt/sbt-proguard/issues/3)
